package view

import model.ObjectPosition
import model.characters.Enemy
import model.characters.Player
import model.map.EmptyTile
import model.map.GameMapObject
import model.map.Wall
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Symbols

/**
 * Transform map objects to characters to display in terminal.
 */
fun GameMapObject.asCharacter() = when(this) {
    Wall -> '#'
    EmptyTile -> '.'
}

/**
 * Transform position used in model to position used in external library.
 */
fun ObjectPosition.toPosition() = Position.of(this.column, this.row)