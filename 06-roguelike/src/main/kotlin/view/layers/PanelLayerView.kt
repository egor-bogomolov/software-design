package view.layers

import model.GameState
import org.codetome.zircon.api.terminal.Terminal
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.graphics.Layer


internal class PanelLayerView(
        override val terminal: Terminal,
        override val size: Size,
        override val offset: Position
): LayerView {
    private var layer: Layer? = null

    override fun draw(state: GameState) {
        layer?.let { terminal.removeLayer(it) }

        val newLayer = LayerBuilder.newBuilder()
                .size(size)
                .offset(offset)
                .build()

        drawPlayerInfo(state, newLayer)
        drawButtons(newLayer)

        terminal.pushLayer(newLayer)
        terminal.flush()

        layer = newLayer
    }

    private fun drawPlayerInfo(state: GameState, layer: Layer) {
        layer.putText("HP: ${state.getPlayer().hp}/${state.getPlayer().maxHp}", Position.of(0, 1))
        layer.putText("Armor: ${state.getPlayer().armor}", Position.of(size.columns / 3, 1))
        layer.putText("Attack:${state.getPlayer().attack}", Position.of(size.columns / 3 * 2, 1))
    }

    private fun drawButtons(layer: Layer) {
        layer.putText("[I]nventory", Position.of(size.columns / 3, 2))
        layer.putText("[Q]uit", Position.of(size.columns / 3 * 2, 2))
    }

}