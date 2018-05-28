import controller.GameController
import model.GameState
import view.GameView

fun main(args: Array<String>) {
    val state = GameState.createNewGame(60, 100)
    val view = GameView.createGameView(40, 80)
    val controller = GameController.createGameController(view, state)
}