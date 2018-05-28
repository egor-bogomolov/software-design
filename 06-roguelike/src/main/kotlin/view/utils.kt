package view

import model.map.EmptyTile
import model.map.GameMapObject
import model.map.Wall

fun GameMapObject.asCharacter() = when(this) {
    Wall -> '#'
    EmptyTile -> '.'
}