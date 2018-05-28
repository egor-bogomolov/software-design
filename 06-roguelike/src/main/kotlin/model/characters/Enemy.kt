package model.characters

import model.ObjectPosition

class Enemy(
        private var position: ObjectPosition
): BasicCharacter {

    var maxHp = 100
    var hp = 100

    override fun moveTo(position: ObjectPosition) {
        this.position = position
    }

    override fun getPosition() = position
}