import model.GameState
import view.GameView

fun main(args: Array<String>) {
    val state = GameState.createGameState(20, 20)
    val view = GameView.createGameView(20, 20, state)
    view.refresh()
}