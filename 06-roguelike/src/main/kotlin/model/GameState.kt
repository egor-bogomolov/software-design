package model

import model.characters.Enemy
import model.characters.Player
import model.map.GameMap
import java.util.*

/**
 * Holds information about game state.
 */
class GameState private constructor(
        val worldHeight: Int,
        val worldWidth: Int,
        private val map: GameMap,
        private val player: Player,
        private val enemies: MutableList<Enemy>
) {


    companion object {
        private const val KEEP_LAST_LOGGED = 100;

        /**
         * Used instead of constructor.
         */
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

    private val loggedEvents = mutableListOf<String>()

    /**
     * Get map.
     */
    fun getMap() = map

    /**
     * Get player.
     */
    fun getPlayer() = player

    /**
     * Get list of enemies.
     */
    fun getEnemies() = enemies

    /**
     * Get enemy at the position or null if there is no enemies there.
     */
    fun getOccupant(position: ObjectPosition) = enemies.firstOrNull { it.getPosition() == position }

    /**
     * Remove enemy from list of enemies.
     */
    fun removeEnemy(enemy: Enemy) {
        enemies.remove(enemy)
    }

    /**
     * Get list of events.
     */
    fun getLog() = loggedEvents

    /**
     * Add event to the list.
     * Only last [KEEP_LAST_LOGGED] are saved.
     */
    fun addEventToLog(event: String) {
        if (loggedEvents.size == KEEP_LAST_LOGGED) {
            loggedEvents.removeAt(0)
        }
        loggedEvents.add(event)
    }
}