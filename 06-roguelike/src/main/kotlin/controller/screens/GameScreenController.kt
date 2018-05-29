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

internal class GameScreenController(
        val view: GameView,
        val state: GameState
) : ScreenController {

    override fun accept(input: Input): InvokationResult {
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
                        } else {
                            val combatResult = combat(state.getPlayer(), occupant)
                            applyCombatResults(state.getPlayer(), occupant, combatResult)
                            if (state.getPlayer().isDead()) {
                                return InvokationResult(LostScreen, true)
                            }
                            if (occupant.isDead()) {
                                state.removeEnemy(occupant)
                                state.getPlayer().moveTo(nextPosition)
                                randomDrop()?.let { state.getPlayer().addItem(it) }
                            }
                        }
                        return InvokationResult(GameScreen, true)
                    }
                }
                InputType.Character -> when(stroke.getCharacter()) {
                    'I', 'i' -> {
                        return InvokationResult(InventoryScreen, true)
                    }
                    'Q', 'q' -> {
                        return InvokationResult(Finished, true)
                    }
                    else -> {}
                }
                else -> {}
            }
        }
        return InvokationResult(GameScreen, false)
    }

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