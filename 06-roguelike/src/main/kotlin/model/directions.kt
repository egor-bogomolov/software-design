package model

sealed class Direction(val dRow: Int, val dColumn: Int, val name: String)

object DirectionUp: Direction(-1, 0, "north")

object DirectionRight: Direction(0, 1, "east")

object DirectionBottom: Direction(1, 0, "south")

object DirectionLeft: Direction(0, -1, "west")
