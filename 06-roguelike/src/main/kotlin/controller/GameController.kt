package controller

import controller.screens.*
import model.*
import org.codetome.zircon.api.input.Input
import view.GameView

/**
 * Controller that rules the game.
 * Receives input from View via Listener.
 */
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
    private val inventoryScreenController = InventoryScreenController(view, state)
    private val enemyController = EnemyController(view, state)

    init {
        view.registerListener(this)
        view.getMapLayer().draw(state)
        view.getPanelLayer().draw(state)
    }

    /**
     * Get input from view, pass it to current active screen and change state according to result of the action.
     */
    override fun onInputAction(input: Input) {
        var result = when(activeScreen) {
            GameScreen -> gameScreenController.accept(input)
            LostScreen -> lostGameScreenController.accept(input)
            InventoryScreen -> inventoryScreenController.accept(input)
            Finished -> InvocationResult(Finished, true)
        }
        if (result.hasChanged) {
            view.clearAll()
            if (result.activeScreen == GameScreen && activeScreen == GameScreen) {
                result = enemyController.moveAllEnemies()
            }
            when(result.activeScreen) {
                GameScreen -> {
                    gameScreenController.draw()
                }
                LostScreen -> lostGameScreenController.draw()
                InventoryScreen -> inventoryScreenController.draw()
                Finished -> view.finish()
            }
            activeScreen = result.activeScreen
        }
    }
}