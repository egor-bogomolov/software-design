package controller.screens

import model.*
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.InputType
import view.GameView
import InvalidArgumentException
import model.characters.combat.applyCombatResults
import model.characters.combat.combat
import model.characters.items.randomDrop
import view.layers.GameMode

/**
 * ScreenController for main game screen.
 */
internal class GameScreenController(
        val view: GameView,
        val state: GameState
) : ScreenController {

    /**
     * {@inheritDoc}
     */
    override fun accept(input: Input): InvocationResult {
        if (input.isKeyStroke()) {
            val stroke = input.asKeyStroke()
            when(stroke.getInputType()) {
                InputType.ArrowDown, InputType.ArrowLeft, InputType.ArrowUp, InputType.ArrowRight -> {
                    val direction = getDirection(stroke.getInputType())
                    val nextPosition = state.getPlayer().getPosition().move(direction)
                    if (state.getMap().isPassableAt(nextPosition)) {
                        val occupant = state.getOccupant(nextPosition)
                        if (occupant == null) {
                            state.getPlayer().moveTo(nextPosition)
                            state.getPlayer().addHp(1)
                            state.addEventToLog("Player moved ${direction.name}")
                        } else {
                            val combatResult = combat(state.getPlayer(), occupant)
                            applyCombatResults(state.getPlayer(), occupant, combatResult)
                            state.addEventToLog(
                                    "Player (-${combatResult.hpReduce1} hp) attacked enemy " +
                                            "(-${combatResult.hpReduce2} hp, ${occupant.getHp()} left)")
                            if (state.getPlayer().isDead()) {
                                state.addEventToLog("Player died!")
                                return InvocationResult(LostScreen, true)
                            }
                            if (occupant.isDead()) {
                                state.removeEnemy(occupant)
                                state.getPlayer().moveTo(nextPosition)
                                val drop = randomDrop()
                                if (drop != null) {
                                    state.getPlayer().addItem(drop)
                                    state.addEventToLog("Enemy died and dropped ${drop.title}")
                                } else {
                                    state.addEventToLog("Enemy died and dropped nothing")
                                }
                            }
                        }
                        return InvocationResult(GameScreen, true)
                    }
                }
                InputType.Character -> when(stroke.getCharacter()) {
                    'I', 'i' -> {
                        return InvocationResult(InventoryScreen, true)
                    }
                    'Q', 'q' -> {
                        return InvocationResult(Finished, true)
                    }
                    else -> {}
                }
                else -> {}
            }
        }
        return InvocationResult(GameScreen, false)
    }

    /**
     * {@inheritDoc}
     */
    override fun draw() {
        view.getMapLayer().draw(state)
        view.getPanelLayer().setMode(GameMode)
        view.getPanelLayer().draw(state)
    }

    private fun getDirection(inputType: InputType) = when(inputType) {
        InputType.ArrowDown -> DirectionBottom
        InputType.ArrowLeft -> DirectionLeft
        InputType.ArrowUp -> DirectionUp
        InputType.ArrowRight -> DirectionRight
        else -> {
            throw InvalidArgumentException("Tried to get direction from non-arrow button.")
        }
    }
}