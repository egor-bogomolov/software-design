package controller

import controller.screens.GameScreen
import controller.screens.InvocationResult
import controller.screens.LostScreen
import model.*
import model.characters.Enemy
import model.characters.combat.applyCombatResults
import model.characters.combat.combat
import model.characters.items.randomDrop
import view.GameView
import kotlin.math.abs

/**
 * Controls logic of enemies.
 */
class EnemyController(
        val view: GameView,
        val state: GameState
) {
    /**
     * Make one step with each enemy.
     * Enemies move to players position if possible.
     */
    fun moveAllEnemies(): InvocationResult {
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
                state.addEventToLog(
                        "Enemy (-${combatResult.hpReduce2} hp, ${enemy.getHp()} left) attacked " +
                                "player (-${combatResult.hpReduce1} hp)")
                if (state.getPlayer().isDead()) {
                    state.addEventToLog("Player died!")
                    return InvocationResult(LostScreen, true)
                }
                if (enemy.isDead()) {
                    toDelete.add(enemy)
                    val drop = randomDrop()
                    if (drop != null) {
                        state.getPlayer().addItem(drop)
                        state.addEventToLog("Enemy died and dropped ${drop.title}")
                    } else {
                        state.addEventToLog("Enemy died and dropped nothing")
                    }
                }
            } else {
                enemy.moveTo(nextPosition)
            }
        }
        for (enemy in toDelete) {
            state.removeEnemy(enemy)
        }
        return InvocationResult(GameScreen, true)
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