package model.map

import model.ObjectPosition
import java.util.*

/**
 * Represents game field with obstacles.
 */
class GameMap(
        height: Int,
        width: Int,
        generateMap: ((Int, Int) -> Array<Array<out GameMapObject>>) = { h, w ->
            generateMapWithRandomObstacles(h, w)
        }
) {

    private val map = generateMap(height, width)

    /**
     * Get map object at a position.
     */
    fun getObjectAt(position: ObjectPosition) = map[position.row][position.column]

    /**
     * Is object at a position passable.
     */
    fun isPassableAt(position: ObjectPosition) = getObjectAt(position).isPassable()

}

private fun generateEmptyMap(height: Int, width: Int) = Array(height, { row ->
    if (row == 0 || row == height - 1) {
        Array(width, { _ -> Wall })
    } else {
        Array(width, { column ->
            if (column == 0 || column == width - 1) {
                Wall
            } else {
                EmptyTile
            }
        })
    }
})

private fun generateMapWithRandomObstacles(height: Int, width: Int) = Array(height, { row ->
    if (row == 0 || row == height - 1) {
        Array(width, { _ -> Wall })
    } else {
        Array(width, { column ->
            if (column == 0 || column == width - 1 || Random().nextInt(10) == 0) {
                Wall
            } else {
                EmptyTile
            }
        })
    }
})

