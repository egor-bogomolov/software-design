package model.map

import model.ObjectPosition
import kotlin.test.*


class GameMapTest {
    private val width = 10
    private val height = 10

    @Test
    fun emptyMapTest() {
        val map = GameMap(height, width, { h, w -> generateEmptyMap(h, w) })
        for (i in 0 until width) {
            for (j in 0 until height) {
                assertTrue(map.isPassableAt(ObjectPosition(i, j)))
                assertEquals(EmptyTile, map.getObjectAt(ObjectPosition(i, j)))
            }
        }
    }

    @Test
    fun wallMapTest() {
        val map = GameMap(height, width, { h, w -> generateWallMap(h, w) })
        for (i in 0 until width) {
            for (j in 0 until height) {
                assertFalse(map.isPassableAt(ObjectPosition(i, j)))
                assertEquals(Wall, map.getObjectAt(ObjectPosition(i, j)))
            }
        }
    }
}

private fun generateEmptyMap(height: Int, width: Int): Array<Array<out GameMapObject>>
        = Array(height, { Array(width, { EmptyTile }) })

private fun generateWallMap(height: Int, width: Int): Array<Array<out GameMapObject>>
        = Array(height, { Array(width, { Wall }) })