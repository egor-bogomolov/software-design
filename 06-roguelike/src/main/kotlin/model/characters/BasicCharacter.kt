package model.characters

import model.Direction
import model.ObjectPosition

interface BasicCharacter {
    fun getPosition(): ObjectPosition
    fun moveTo(position: ObjectPosition)
}