package model.characters

import model.Direction
import model.ObjectPosition

interface BasicCharacter {
    var position: ObjectPosition

    fun move(direction: Direction) {
        position = ObjectPosition( position.column + direction.dColumn, position.row + direction.dRow)
    }
}