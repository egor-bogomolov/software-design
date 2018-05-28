import controller.GameController
import model.GameState
import view.GameView

fun main(args: Array<String>) {
    val state = GameState.createNewGame(20, 20)
    val view = GameView.createGameView(20, 20)
    val controller = GameController.createGameController(view, state)
}