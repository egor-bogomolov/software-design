package view.layers

import model.GameState
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.terminal.Terminal

internal class LostGameLayerView(
        override val terminal: Terminal,
        override val size: Size,
        override val offset: Position
) : LayerView {

    companion object {
        private const val LOST_TEXT = "YOU LOST!!!"
        private const val FRAME_SIZE = 3
    }

    private var layer: Layer? = null

    override fun draw(state: GameState) {
        layer?.let { terminal.removeLayer(it) }

        val newLayer = LayerBuilder.newBuilder()
                .size(size)
                .offset(offset)
                .build()

        drawLostText(newLayer)

        terminal.pushLayer(newLayer)
        terminal.flush()

        layer = newLayer
    }

    private fun drawLostText(layer: Layer) {
        val h = size.rows / 2
        val w = (size.columns - LOST_TEXT.length) / 2
        for (row in (h - FRAME_SIZE)..(h + FRAME_SIZE)) {
            for (col in (w - FRAME_SIZE) until (w + LOST_TEXT.length + FRAME_SIZE)) {
                layer.setCharacterAt(Position.of(col, row), ' ')
            }
        }
        layer.putText(LOST_TEXT, Position.of(w, h))
    }
}