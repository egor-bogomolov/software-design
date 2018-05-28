package model.characters

import model.Direction

interface BasicCharacter {
    var row: Int
    var column: Int

    fun move(direction: Direction) {
        row += direction.dRow
        column += direction.dColumn
    }
}