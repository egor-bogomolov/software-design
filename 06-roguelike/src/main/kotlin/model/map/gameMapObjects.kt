package model.map

sealed class GameMapObject {
    abstract fun isPassable(): Boolean
}

object Wall : GameMapObject() {
    override fun isPassable() = false
}

object EmptyTile : GameMapObject() {
    override fun isPassable() = true
}