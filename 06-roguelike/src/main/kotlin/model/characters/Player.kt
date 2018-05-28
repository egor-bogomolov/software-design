package model.characters

class Player(
        override var row: Int,
        override var column: Int
): BasicCharacter {
    var maxHp = 100
    var hp = 100

}