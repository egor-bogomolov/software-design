package model.characters

import model.ObjectPosition
import model.characters.combat.CombatCharacter
import model.characters.items.Item
import kotlin.math.max
import kotlin.math.min

/**
 * Main character.
 */
class Player(
        private var position: ObjectPosition
): BasicCharacter, CombatCharacter {

    companion object {
        private const val MAX_HP = 100
        private const val BASE_ATTACK = 10
        private const val BASE_ARMOR = 0
    }

    private var hp = MAX_HP

    /**
     * Sum of base armor and stats of equipped items.
     */
    override fun getArmor() = BASE_ARMOR + equippedItems.sumBy { it.armor }


    /**
     * Sum of base attack and stats of equipped items.
     */
    override fun getAttack() = BASE_ATTACK+ equippedItems.sumBy { it.attack }

    /**
     * Sum of base max hp and stats of equipped items.
     */
    override fun getMaxHp() = MAX_HP + + equippedItems.sumBy { it.hp }

    /**
     * {@inheritDoc}
     */
    override fun getHp(): Int {
        normalizeHp()
        return hp
    }

    /**
     * {@inheritDoc}
     */
    override fun addHp(hp: Int) {
        this.hp += hp
        normalizeHp()
    }

    /**
     * Fit hp in range 0..MaxHP
     */
    private fun normalizeHp() {
        this.hp = min(this.hp, getMaxHp())
        this.hp = max(this.hp, 0)
    }

    /**
     * {@inheritDoc}
     */
    override fun getPosition() = position

    /**
     * {@inheritDoc}
     */
    override fun moveTo(position: ObjectPosition) {
        this.position = position
    }

    private val items = mutableListOf<Item>()
    private val equippedItems = mutableListOf<Item>()

    /**
     * Get list of all items that player has.
     */
    fun getItems() = items

    /**
     * Check if item is equipped.
     */
    fun isEquipped(item: Item) = item in equippedItems

    /**
     * Equip item by index in list of items.
     */
    fun equip(index: Int) {
        val item = items.get(index)
        equippedItems.removeIf { it::class == item::class }
        equippedItems.add(item)
    }

    /**
     * Add item to list of items.
     */
    fun addItem(item: Item) {
        items.add(item)
    }
}