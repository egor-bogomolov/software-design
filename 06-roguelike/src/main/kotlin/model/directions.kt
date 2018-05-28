package model

sealed class Direction(val dRow: Int, val dColumn: Int)

object DirectionUp: Direction(-1, 0)

object DirectionRight: Direction(0, 1)

object DirectionBottom: Direction(1, 0)

object DirectionLeft: Direction(0, -1)
