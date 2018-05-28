package model.characters

import model.ObjectPosition
import model.characters.combat.CombatCharacter

class Player(
        private var position: ObjectPosition
): BasicCharacter, CombatCharacter {

    companion object {
        private const val MAX_HP = 100
        private const val BASE_ATTACK = 1
        private const val BASE_ARMOR = 0
    }

    private var hp = MAX_HP

    override fun getArmor() = BASE_ARMOR

    override fun getAttack() = BASE_ATTACK

    override fun getMaxHp() = MAX_HP

    override fun getHp() = hp

    override fun isDead() = hp <= 0

    override fun reduceHp(hp: Int) {
        this.hp -= hp
    }

    override fun getPosition() = position

    override fun moveTo(position: ObjectPosition) {
        this.position = position
    }

}