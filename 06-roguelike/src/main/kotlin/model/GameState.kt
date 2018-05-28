package model

import model.characters.Enemy
import model.characters.Player
import model.map.GameMap

class GameState private constructor(
        val worldHeight: Int,
        val worldWidth: Int,
        val map: GameMap,
        val player: Player,
        val enemies: List<Enemy>
) {


    companion object {
        fun createNewGame(height: Int, width: Int): GameState {
            val player = Player(height / 2, width / 2)
            val enemies = listOf<Enemy>(
                    Enemy(1, 1), Enemy(4, 1), Enemy(height - 2, width - 2)
            )
            return GameState(height, width, GameMap(height, width), player, enemies)
        }
    }



}