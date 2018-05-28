package controller.screens

import model.GameState
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.InputType
import view.GameView

internal class LostGameScreenController(
        val view: GameView,
        val state: GameState
) : ScreenController {
    override fun accept(input: Input): ActiveScreen {
        if (input.isKeyStroke()) {
            val stroke = input.asKeyStroke()
            when(stroke.getInputType()) {
                InputType.Character -> when(stroke.getCharacter()) {
                    'Q', 'q' -> {
                        view.finish()
                        return Finished
                    }
                    else -> {}
                }
                else -> {}
            }
        }
        return LostScreen
    }
}