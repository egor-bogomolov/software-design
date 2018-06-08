import controller.GameController
import model.GameState
import view.GameView

/**
 * Start the Roguelike
 */
fun main(args: Array<String>) {
    val state = GameState.createNewGame(60, 100)
    val view = GameView.createGameView(40, 80)
    val controller = GameController.createGameController(view, state)
}