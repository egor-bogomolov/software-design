package model

import model.map.GameMap

class GameState private constructor(
        val worldHeight: Int,
        val worldWidth: Int,
        val map: GameMap
) {

    companion object {
        fun createGameState(height: Int, width: Int) = GameState(height, width, GameMap(height, width))
    }



}