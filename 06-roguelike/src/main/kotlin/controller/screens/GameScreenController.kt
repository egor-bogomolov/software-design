package controller.screens

import model.*
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.InputType
import view.GameView
import InvalidArgumentException
import model.characters.combat.applyCombatResults
import model.characters.combat.combat
import model.characters.items.randomDrop

internal class GameScreenController(
        val view: GameView,
        val state: GameState
) : ScreenController {

    override fun accept(input: Input): ActiveScreen {
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
                        } else {
                            val combatResult = combat(state.getPlayer(), occupant)
                            applyCombatResults(state.getPlayer(), occupant, combatResult)
                            if (state.getPlayer().isDead()) {
                                view.getPanelLayer().draw(state)
                                view.getLostGameLayer().draw(state)
                                return LostScreen
                            }
                            if (occupant.isDead()) {
                                state.removeEnemy(occupant)
                                state.getPlayer().moveTo(nextPosition)
                                randomDrop()?.let { state.getPlayer().addItem(it) }
                            }
                        }
                        view.getMapLayer().draw(state)
                        view.getPanelLayer().draw(state)
                    }
                    return GameScreen
                }
                InputType.Character -> when(stroke.getCharacter()) {
                    'I', 'i' -> {
                        println("I")
                    }
                    'Q', 'q' -> {
                        view.finish()
                        return Finished
                    }
                    else -> {}
                }
                else -> {}
            }
        }
        return GameScreen
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