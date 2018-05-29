package model

import model.characters.Enemy
import model.characters.Player
import model.map.GameMap
import java.util.*

class GameState private constructor(
        val worldHeight: Int,
        val worldWidth: Int,
        private val map: GameMap,
        private val player: Player,
        private val enemies: MutableList<Enemy>
) {


    companion object {
        fun createNewGame(height: Int, width: Int, numEnemies: Int = 10): GameState {
            val player = Player(ObjectPosition(width / 2, height / 2))
            val enemies = mutableListOf<Enemy>()
            for (i in 1..numEnemies) {
                var position = ObjectPosition(Random().nextInt(width), Random().nextInt(height))
                while(position == player.getPosition() || enemies.any { position == it.getPosition() }) {
                    position = ObjectPosition(Random().nextInt(width), Random().nextInt(height))
                }
                enemies.add(Enemy(position))
            }
            return GameState(height, width, GameMap(height, width), player, enemies)
        }
    }

    fun getMap() = map

    fun getPlayer() = player

    fun getEnemies() = enemies

    fun getOccupant(position: ObjectPosition) = enemies.firstOrNull { it.getPosition() == position }

    fun removeEnemy(enemy: Enemy) {
        enemies.remove(enemy)
    }
}