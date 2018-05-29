package model.characters

import model.ObjectPosition
import model.characters.combat.CombatCharacter
import model.characters.items.Item
import ItemNotExist
import kotlin.math.max
import kotlin.math.min

class Player(
        private var position: ObjectPosition
): BasicCharacter, CombatCharacter {

    companion object {
        private const val MAX_HP = 100
        private const val BASE_ATTACK = 10
        private const val BASE_ARMOR = 0
    }

    private var hp = MAX_HP

    override fun getArmor() = BASE_ARMOR + equippedItems.sumBy { it.armor }

    override fun getAttack() = BASE_ATTACK+ equippedItems.sumBy { it.attack }

    override fun getMaxHp() = MAX_HP + + equippedItems.sumBy { it.hp }

    override fun getHp(): Int {
        normalizeHp()
        return hp
    }

    override fun isDead() = hp <= 0

    override fun addHp(hp: Int) {
        this.hp += hp
        normalizeHp()
    }

    private fun normalizeHp() {
        this.hp = min(this.hp, getMaxHp())
        this.hp = max(this.hp, 0)
    }

    override fun getPosition() = position

    override fun moveTo(position: ObjectPosition) {
        this.position = position
    }

    private val items = mutableListOf<Item>()
    private val equippedItems = mutableListOf<Item>()

    fun getItems() = items

    fun isEquipped(item: Item) = item in equippedItems

    fun equip(index: Int) {
        val item = items.get(index)
        equippedItems.removeIf { it::class == item::class }
        equippedItems.add(item)
    }

    fun addItem(item: Item) {
        items.add(item)
    }
}