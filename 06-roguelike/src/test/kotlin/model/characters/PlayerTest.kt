package model.characters

import model.ObjectPosition
import model.characters.items.Amulet
import model.characters.items.Helmet
import model.characters.items.Sword
import kotlin.test.*


class PlayerTest {
    private val startPosition = ObjectPosition(0, 0)
    private val finishPosition = ObjectPosition(1, 1)
    private val helmet = Helmet(5)
    private val superHelmet = Helmet(10)
    private val sword = Sword(5)
    private val amulet = Amulet(5)
    private var player = Player(startPosition)

    @BeforeTest
    fun setUp() {
        player = Player(startPosition)
    }

    @Test
    fun changePosition() {
        assertEquals(startPosition, player.getPosition())
        player.moveTo(finishPosition)
        assertEquals(finishPosition, player.getPosition())
    }

    @Test
    fun equipItem() {
        assertFalse(player.isEquipped(helmet))
        player.addItem(helmet)
        assertFalse(player.isEquipped(helmet))
        player.equip(player.getItems().indexOf(helmet))
        assertTrue(player.isEquipped(helmet))
    }

    @Test
    fun equipItemOfSameType() {
        player.addItem(helmet)
        player.addItem(superHelmet)
        assertFalse(player.isEquipped(helmet))
        assertFalse(player.isEquipped(superHelmet))
        player.equip(player.getItems().indexOf(helmet))
        assertTrue(player.isEquipped(helmet))
        assertFalse(player.isEquipped(superHelmet))
        player.equip(player.getItems().indexOf(superHelmet))
        assertFalse(player.isEquipped(helmet))
        assertTrue(player.isEquipped(superHelmet))
    }

    @Test
    fun statsWithItem() {
        assertEquals(Player.BASE_ARMOR, player.getArmor())
        player.addItem(helmet)
        player.equip(player.getItems().indexOf(helmet))
        assertEquals(Player.BASE_ARMOR + helmet.armor, player.getArmor())

        assertEquals(Player.BASE_ATTACK, player.getAttack())
        player.addItem(sword)
        player.equip(player.getItems().indexOf(sword))
        assertEquals(Player.BASE_ATTACK + sword.attack, player.getAttack())

        assertEquals(Player.MAX_HP, player.getMaxHp())
        player.addItem(amulet)
        player.equip(player.getItems().indexOf(amulet))
        assertEquals(Player.MAX_HP + amulet.hp, player.getMaxHp())
    }

    @Test
    fun addHp() {
        assertEquals(Player.MAX_HP, player.getHp())
        player.reduceHp(10)
        assertEquals(Player.MAX_HP - 10, player.getHp())
        player.addHp(20)
        assertEquals(Player.MAX_HP, player.getHp())
        player.reduceHp(Player.MAX_HP * 2)
        assertEquals(0, player.getHp())
    }
}
