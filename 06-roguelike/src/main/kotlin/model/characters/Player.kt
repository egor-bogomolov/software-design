package model.characters

import model.ObjectPosition

class Player(
        override var position: ObjectPosition
): BasicCharacter {
    var maxHp = 100
    var hp = 100

}