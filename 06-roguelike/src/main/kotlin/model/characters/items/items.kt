package model.characters.items

import java.util.*

sealed class Item(val hp: Int, val armor: Int, val attack: Int, val title: String)

class Amulet(hp: Int) : Item(hp, 0, 0, "amulet")

class Helmet(armor: Int) : Item(0, armor, 0, "helmet")

class Sword(attack: Int) : Item(0, 0, attack, "sword")

fun randomDrop(): Item? = when (Random().nextInt(4)) {
    0 -> Amulet(Random().nextInt(20) + 10)
    1 -> Helmet(Random().nextInt(5) + 1)
    2 -> Sword(Random().nextInt(5) + 1)
    else -> null
}
