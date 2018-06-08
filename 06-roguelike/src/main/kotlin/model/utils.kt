package model

/**
 * 2D-position on map.
 */
data class ObjectPosition(val column: Int, val row: Int) {
    operator fun plus(other: ObjectPosition) = ObjectPosition(column + other.column, row + other.row)

    operator fun minus(other: ObjectPosition) = ObjectPosition(column - other.column, row - other.row)
}

/**
 * Get position corresponding to one step in the direction from current position.
 */
fun ObjectPosition.move(direction: Direction) = ObjectPosition(column + direction.dColumn, row + direction.dRow)
