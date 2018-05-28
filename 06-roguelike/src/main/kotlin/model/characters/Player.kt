package model.characters

import model.ObjectPosition

class Player(
        private var position: ObjectPosition
): BasicCharacter {

    var maxHp = 100
    var hp = 100

    override fun getPosition() = position

    override fun moveTo(position: ObjectPosition) {
        this.position = position
    }
}