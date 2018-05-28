package controller

import controller.screens.*
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

    private var activeScreen: ActiveScreen = GameScreen
    private val gameScreenController = GameScreenController(view, state)
    private val lostGameScreenController = LostGameScreenController(view, state)

    init {
        view.registerListener(this)
        view.getMapLayer().draw(state)
        view.getPanelLayer().draw(state)
    }

    override fun onInputAction(input: Input) {
        activeScreen = when(activeScreen) {
            GameScreen -> gameScreenController.accept(input)
            LostScreen -> lostGameScreenController.accept(input)
            Finished -> Finished
        }
    }
}