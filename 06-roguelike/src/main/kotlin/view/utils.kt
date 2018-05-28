package view

import model.characters.Enemy
import model.characters.Player
import model.map.EmptyTile
import model.map.GameMapObject
import model.map.Wall
import org.codetome.zircon.api.Symbols

fun GameMapObject.asCharacter() = when(this) {
    Wall -> '#'
    EmptyTile -> '.'
}

fun Player.asCharacter() = Symbols.MALE

fun Enemy.asCharacter() = Symbols.FEMALE