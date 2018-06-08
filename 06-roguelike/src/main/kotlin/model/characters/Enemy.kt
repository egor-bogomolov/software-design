package model.characters

import model.ObjectPosition
import model.characters.combat.CombatCharacter
import kotlin.math.max
import kotlin.math.min

/**
 * Represents monster. Has parameters inherited from combat character and basic character.
 */
class Enemy(
        private var position: ObjectPosition
): BasicCharacter, CombatCharacter {

    companion object {
        private const val MAX_HP = 100
        private const val BASE_ATTACK = 5
        private const val BASE_ARMOR = 1
    }

    private var hp = MAX_HP

    /**
     * {@inheritDoc}
     */
    override fun getArmor() = BASE_ARMOR

    /**
     * {@inheritDoc}
     */
    override fun getAttack() = BASE_ATTACK

    /**
     * {@inheritDoc}
     */
    override fun getMaxHp() = MAX_HP

    /**
     * {@inheritDoc}
     */
    override fun getHp() = hp

    /**
     * {@inheritDoc}
     */
    override fun addHp(hp: Int) {
        this.hp += hp
        this.hp = min(this.hp, getMaxHp())
        this.hp = max(this.hp, 0)
    }

    /**
     * {@inheritDoc}
     */
    override fun moveTo(position: ObjectPosition) {
        this.position = position
    }

    /**
     * {@inheritDoc}
     */
    override fun getPosition() = position
}