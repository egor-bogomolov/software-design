package model.map

class GameMap(
        height: Int,
        width: Int,
        generateMap: ((Int, Int) -> Array<Array<out GameMapObject>>) = { h, w ->
            generateEmptyMap(h, w)
        }
) {

    private val map = generateMap(width, height)

    fun getObjectAt(row: Int, column: Int) = map.elementAt(row).elementAt(column)

    fun isPassableAt(row: Int, column: Int) = getObjectAt(row, column).isPassable()

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
