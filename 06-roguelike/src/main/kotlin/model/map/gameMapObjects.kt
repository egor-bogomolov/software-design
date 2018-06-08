package model.map

/**
 * Represents object on map.
 */
sealed class GameMapObject {
    abstract fun isPassable(): Boolean
}

/**
 * Non-passable tile.
 */
object Wall : GameMapObject() {
    override fun isPassable() = false
}

/**
 * Passable tile.
 */
object EmptyTile : GameMapObject() {
    override fun isPassable() = true
}