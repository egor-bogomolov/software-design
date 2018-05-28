import controller.GameController
import model.GameState
import view.GameView

fun main(args: Array<String>) {
    val state = GameState.createNewGame(100, 100)
    val view = GameView.createGameView(50, 50)
    val controller = GameController.createGameController(view, state)
}