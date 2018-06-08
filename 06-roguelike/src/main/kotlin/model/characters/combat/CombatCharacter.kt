package model.characters.combat

/**
 * Represents a character that can fight.
 */
interface CombatCharacter {
    /**
     * Current armor.
     */
    fun getArmor(): Int

    /**
     * Current attack.
     */
    fun getAttack(): Int

    /**
     * Current max hp.
     */
    fun getMaxHp(): Int

    /**
     * Current hp.
     */
    fun getHp(): Int

    /**
     * Is character dead or alive.
     */
    fun isDead(): Boolean = getHp() <= 0

    /**
     * Remove hp.
     */
    fun reduceHp(hp: Int) = addHp(-hp)

    /**
     * Add hp.
     */
    fun addHp(hp: Int)
}