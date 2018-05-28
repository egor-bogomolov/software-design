package controller

import model.GameState
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
        view.draw(state)
    }

    override fun onInputAction(input: Input) {
        if (input.isKeyStroke()) {
            val stroke = input.asKeyStroke()
            when(stroke.getInputType()) {
                InputType.ArrowDown -> {println("Down")}
                InputType.ArrowLeft -> {println("Left")}
                InputType.ArrowUp -> {}
                InputType.ArrowRight -> {}
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