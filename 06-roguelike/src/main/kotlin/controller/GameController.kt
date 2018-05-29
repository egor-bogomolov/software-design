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
    private val inventoryScreenController = InventoryScreenController(view, state)
    private val enemyController = EnemyController(view, state)

    init {
        view.registerListener(this)
        view.getMapLayer().draw(state)
        view.getPanelLayer().draw(state)
    }

    override fun onInputAction(input: Input) {
        val result = when(activeScreen) {
            GameScreen -> gameScreenController.accept(input)
            LostScreen -> lostGameScreenController.accept(input)
            InventoryScreen -> inventoryScreenController.accept(input)
            Finished -> InvokationResult(Finished, true)
        }
        if (result.hasChanged) {
            view.clearAll()
            when(result.activeScreen) {
                GameScreen -> {
                    if (activeScreen == GameScreen) {
                        enemyController.moveAllEnemies()
                    }
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