package model.characters.combat

interface CombatCharacter {
    fun getArmor(): Int
    fun getAttack(): Int
    fun getMaxHp(): Int
    fun getHp(): Int
    fun isDead(): Boolean
    fun reduceHp(hp: Int)
}