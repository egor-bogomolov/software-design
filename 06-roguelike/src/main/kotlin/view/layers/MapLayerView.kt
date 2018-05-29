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

internal class MapLayerView(
        override val terminal: Terminal,
        override val size: Size,
        override val offset: Position
) : LayerView {

    companion object {
        private const val PLAYER_SYMBOL = Symbols.MALE
        private const val ENEMY_SYMBOL = Symbols.FEMALE
    }

    private var layer: Layer? = null

    override fun clear() {
        layer?.let { terminal.removeLayer(it) }
        layer = null
    }

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

    private fun drawPlayer(state: GameState, layer: Layer, drawingOffset: ObjectPosition) {
        layer.setCharacterAt((state.getPlayer().getPosition() - drawingOffset).toPosition(), PLAYER_SYMBOL)
    }

    private fun drawEnemies(state: GameState, layer: Layer, drawingOffset: ObjectPosition) {
        state.getEnemies().forEach {
            val position = it.getPosition() - drawingOffset
            if (position.isValid()) {
                layer.setCharacterAt(position.toPosition(), ENEMY_SYMBOL)
            }
        }
    }

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