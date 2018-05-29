package model.characters

import model.ObjectPosition
import model.characters.combat.CombatCharacter
import kotlin.math.max
import kotlin.math.min

class Enemy(
        private var position: ObjectPosition
): BasicCharacter, CombatCharacter {

    companion object {
        private const val MAX_HP = 100
        private const val BASE_ATTACK = 1
        private const val BASE_ARMOR = 1
    }

    private var hp = MAX_HP

    override fun getArmor() = BASE_ARMOR

    override fun getAttack() = BASE_ATTACK

    override fun getMaxHp() = MAX_HP

    override fun getHp() = hp

    override fun isDead() = hp <= 0

    override fun addHp(hp: Int) {
        this.hp += hp
        this.hp = min(this.hp, getMaxHp())
        this.hp = max(this.hp, 0)
    }

    override fun moveTo(position: ObjectPosition) {
        this.position = position
    }

    override fun getPosition() = position
}