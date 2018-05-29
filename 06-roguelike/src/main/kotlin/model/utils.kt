package model

data class ObjectPosition(val column: Int, val row: Int) {
    operator fun plus(other: ObjectPosition) = ObjectPosition(column + other.column, row + other.row)

    operator fun minus(other: ObjectPosition) = ObjectPosition(column - other.column, row - other.row)
}

fun ObjectPosition.move(direction: Direction) = ObjectPosition(column + direction.dColumn, row + direction.dRow)
