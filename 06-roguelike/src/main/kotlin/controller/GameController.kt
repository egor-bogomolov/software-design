package controller

import model.*
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.InputType
import view.GameView

class GameController(
        val view: GameView,
        val state: GameState
): GameView.Listener {

    companion object {
        fun createGameController(view: GameView, state: GameState) =
                GameController(view, state)
    }

    init {
        view.registerListener(this)
        view.getMapLayer().draw(state)
        view.getPanelLayer().draw(state)
    }

    override fun onInputAction(input: Input) {
        if (input.isKeyStroke()) {
            val stroke = input.asKeyStroke()
            when(stroke.getInputType()) {
                InputType.ArrowDown -> {
                    println("Down")
                    state.movePlayer(DirectionBottom)
                    view.getMapLayer().draw(state)
                }
                InputType.ArrowLeft -> {
                    println("Left")
                    state.movePlayer(DirectionLeft)
                    view.getMapLayer().draw(state)
                }
                InputType.ArrowUp -> {
                    state.movePlayer(DirectionUp)
                    view.getMapLayer().draw(state)
                }
                InputType.ArrowRight -> {
                    state.movePlayer(DirectionRight)
                    view.getMapLayer().draw(state)
                }
                InputType.Character -> when(stroke.getCharacter()) {
                    'I', 'i' -> {
                        println("I")
                    }
                    'Q', 'q' -> {}
                    else -> {}
                }
                else -> {}
            }
        }
    }
}