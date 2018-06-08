package model.characters

import model.Direction
import model.ObjectPosition

/**
 * Character with a position.
 */
interface BasicCharacter {
    /**
     * Get current position.
     */
    fun getPosition(): ObjectPosition

    /**
     * Change current position.
     */
    fun moveTo(position: ObjectPosition)
}