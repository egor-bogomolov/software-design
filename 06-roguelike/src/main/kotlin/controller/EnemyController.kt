package controller

import controller.screens.GameScreen
import controller.screens.InvokationResult
import controller.screens.LostScreen
import model.*
import model.characters.Enemy
import model.characters.combat.applyCombatResults
import model.characters.combat.combat
import model.characters.items.randomDrop
import view.GameView
import kotlin.math.abs

class EnemyController(
        val view: GameView,
        val state: GameState
) {
    fun moveAllEnemies(): InvokationResult {
        val toDelete = mutableListOf<Enemy>()
        for (enemy in state.getEnemies()) {
            val direction = getDirection(state.getPlayer().getPosition(), enemy.getPosition())
            val nextPosition = enemy.getPosition().move(direction)
            if (!state.getMap().isPassableAt(nextPosition) || state.getOccupant(nextPosition) != null) {
                continue
            }
            if (nextPosition == state.getPlayer().getPosition()) {
                val combatResult = combat(state.getPlayer(), enemy)
                applyCombatResults(state.getPlayer(), enemy, combatResult)
                if (state.getPlayer().isDead()) {
                    return InvokationResult(LostScreen, true)
                }
                if (enemy.isDead()) {
                    toDelete.add(enemy)
                    randomDrop()?.let { state.getPlayer().addItem(it) }
                }
            } else {
                enemy.moveTo(nextPosition)
            }
        }
        for (enemy in toDelete) {
            state.removeEnemy(enemy)
        }
        return InvokationResult(GameScreen, true)
    }

    private fun getDirection(playerPos: ObjectPosition, enemyPos: ObjectPosition): Direction {
        val dh = playerPos.row - enemyPos.row
        val dw = playerPos.column - enemyPos.column
        if (abs(dh) > abs(dw)) {
            return if (dh < 0) {
                DirectionUp
            } else {
                DirectionBottom
            }
        } else {
            return if (dw < 0) {
                DirectionLeft
            } else {
                DirectionRight
            }
        }
    }
}