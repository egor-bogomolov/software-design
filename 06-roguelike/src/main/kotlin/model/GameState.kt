package model

import model.characters.Enemy
import model.characters.Player
import model.map.GameMap

class GameState private constructor(
        val worldHeight: Int,
        val worldWidth: Int,
        private val map: GameMap,
        private val player: Player,
        private val enemies: MutableList<Enemy>
) {


    companion object {
        fun createNewGame(height: Int, width: Int): GameState {
            val player = Player(ObjectPosition(width / 2, height / 2))
            val enemies = mutableListOf<Enemy>(
                    Enemy(ObjectPosition(1, 1)), Enemy(ObjectPosition(4, 1))
            )
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