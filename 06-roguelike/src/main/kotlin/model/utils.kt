package model

data class ObjectPosition(val column: Int, val row: Int)

fun ObjectPosition.move(direction: Direction) = ObjectPosition(column + direction.dColumn, row + direction.dRow)