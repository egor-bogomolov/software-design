package view.layers

import model.GameState
import model.ObjectPosition
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.Symbols
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.terminal.Terminal
import view.asCharacter
import view.toPosition

/**
 * LayerVIew representing game map.
 */
internal class MapLayerView(
        override val terminal: Terminal,
        override val size: Size,
        override val offset: Position
) : LayerView {

    companion object {
        private const val PLAYER_SYMBOL = Symbols.FACE_BLACK
        private const val ENEMY_SYMBOL = Symbols.FACE_WHITE
    }

    private var layer: Layer? = null

    /**
     * {@inheritDoc}
     */
    override fun clear() {
        layer?.let { terminal.removeLayer(it) }
        layer = null
    }

    /**
     * {@inheritDoc}
     */
    override fun draw(state: GameState) {
        val newLayer = LayerBuilder.newBuilder()
                .size(size)
                .offset(offset)
                .build()

        val drawingOffset = computeDrawingOffset(state)
        drawMap(state, newLayer, drawingOffset)
        drawPlayer(state, newLayer, drawingOffset)
        drawEnemies(state, newLayer, drawingOffset)

        terminal.pushLayer(newLayer)
        terminal.flush()

        layer = newLayer
    }

    /**
     * Draw map with all the map objects on it.
     */
    private fun drawMap(state: GameState, layer: Layer, drawingOffset: ObjectPosition) {
        for (col in 0 until size.columns) {
            for (row in 0 until size.rows) {
                layer.setCharacterAt(
                        Position.of(col, row),
                        state.getMap().getObjectAt(ObjectPosition(col, row) + drawingOffset).asCharacter()
                )
            }
        }
    }

    /**
     * Draw player position.
     */
    private fun drawPlayer(state: GameState, layer: Layer, drawingOffset: ObjectPosition) {
        layer.setForegroundColor(ANSITextColor.GREEN)
        layer.setCharacterAt((state.getPlayer().getPosition() - drawingOffset).toPosition(), PLAYER_SYMBOL)
        layer.resetColorsAndModifiers()
    }

    /**
     * Draw enemies positions.
     */
    private fun drawEnemies(state: GameState, layer: Layer, drawingOffset: ObjectPosition) {
        state.getEnemies().forEach {
            val position = it.getPosition() - drawingOffset
            if (position.isValid()) {
                layer.setForegroundColor(ANSITextColor.RED)
                layer.setCharacterAt(position.toPosition(), ENEMY_SYMBOL)
                layer.resetColorsAndModifiers()
            }
        }
    }

    /**
     * If possible, player is drawn in center of the screen.
     */
    private fun computeDrawingOffset(state: GameState): ObjectPosition {
        val position = state.getPlayer().getPosition()
        val halfWidth = size.columns / 2
        val halfHeight = size.rows / 2
        val offsetColumns = if (position.column < halfWidth) {
            0
        } else if (position.column + halfWidth < state.worldWidth) {
            position.column - halfWidth
        } else {
            state.worldWidth - size.columns
        }

        val offsetRows = if (position.row < halfHeight) {
            0
        } else if (position.row + halfHeight < state.worldHeight) {
            position.row - halfHeight
        } else {
            state.worldHeight - size.rows
        }
        return ObjectPosition(offsetColumns, offsetRows)
    }

    private fun ObjectPosition.isValid() =
            0 <= column &&
            column < size.columns &&
            0 <= row &&
            row < size.rows

}