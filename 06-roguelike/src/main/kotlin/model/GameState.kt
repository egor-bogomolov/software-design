package model

import model.characters.Enemy
import model.characters.Player
import model.map.GameMap

class GameState private constructor(
        val worldHeight: Int,
        val worldWidth: Int,
        private val map: GameMap,
        private val player: Player,
        private val enemies: List<Enemy>
) {


    companion object {
        fun createNewGame(height: Int, width: Int): GameState {
            val player = Player(ObjectPosition(height / 2, width / 2))
            val enemies = listOf<Enemy>(
                    Enemy(ObjectPosition(1, 1)), Enemy(ObjectPosition(4, 1))
            )
            return GameState(height, width, GameMap(height, width), player, enemies)
        }
    }

    fun getMap() = map

    fun getPlayer() = player

    fun getEnemies() = enemies
}