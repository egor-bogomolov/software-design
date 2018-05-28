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

    override fun draw(state: GameState) {
        layer?.let { terminal.removeLayer(it) }

        val newLayer = LayerBuilder.newBuilder()
                .size(size)
                .offset(offset)
                .build()

        drawMap(state, newLayer)
        drawPlayer(state, newLayer)
        drawEnemies(state, newLayer)

        terminal.pushLayer(newLayer)
        terminal.flush()

        layer = newLayer
    }

    private fun drawMap(state: GameState, layer: Layer) {
        for (col in 0 until size.columns) {
            for (row in 0 until size.rows) {
                layer.setCharacterAt(
                        Position.of(col, row),
                        state.getMap().getObjectAt(ObjectPosition(col, row)).asCharacter()
                )
            }
        }
    }

    private fun drawPlayer(state: GameState, layer: Layer) {
        layer.setCharacterAt(state.getPlayer().getPosition().toPosition(), PLAYER_SYMBOL)
    }

    private fun drawEnemies(state: GameState, layer: Layer) {
        state.getEnemies().forEach {
            layer.setCharacterAt(it.getPosition().toPosition(), ENEMY_SYMBOL)
        }
    }
}