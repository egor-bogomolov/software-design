package controller.screens

import model.GameState
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.InputType
import view.GameView

/**
 * ScreenController for lost game screen.
 */
internal class LostGameScreenController(
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
                InputType.Character -> when(stroke.getCharacter()) {
                    'Q', 'q' -> {
                        return InvocationResult(Finished, true)
                    }
                    else -> {}
                }
                else -> {}
            }
        }
        return InvocationResult(LostScreen, false)
    }

    /**
     * {@inheritDoc}
     */
    override fun draw() {
        view.getPanelLayer().draw(state)
        view.getLostGameLayer().draw(state)
    }

}