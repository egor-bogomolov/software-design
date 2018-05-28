package controller.screens

import model.*
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.InputType
import view.GameView
import InvalidArgumentException

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
                        state.getPlayer().moveTo(nextPosition)
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