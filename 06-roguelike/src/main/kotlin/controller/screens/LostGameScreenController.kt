package controller.screens

import model.GameState
import org.codetome.zircon.api.input.Input
import view.GameView

internal class LostGameScreenController(
        val view: GameView,
        val state: GameState
) : ScreenController {
    override fun accept(input: Input): ActiveScreen {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}