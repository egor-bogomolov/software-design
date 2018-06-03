package view.layers

import model.GameState
import model.ObjectPosition
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.terminal.Terminal
import view.asCharacter

/**
 * LayerView representing inventory screen.
 */
class InventoryLayerView(
        override val terminal: Terminal,
        override val size: Size,
        override val offset: Position
) : LayerView {

    private var layer: Layer? = null
    private var selectedItemIndex = 0

    /**
     * Set index of selected item in the list.
     */
    fun setSelectedItem(index: Int) {
        selectedItemIndex = index
    }

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

        clearBoard(newLayer)
        drawItems(state, newLayer)

        terminal.pushLayer(newLayer)
        terminal.flush()

        layer = newLayer
    }

    /**
     * Replace everything drawn with spaces.
     */
    private fun clearBoard(layer: Layer) {
        for (col in 0 until size.columns) {
            for (row in 0 until size.rows) {
                layer.setCharacterAt(
                        Position.of(col, row),
                        ' '
                )
            }
        }
    }

    /**
     * Draw list of items.
     */
    private fun drawItems(state: GameState, layer: Layer) {
        val items = state.getPlayer().getItems()
        var curIndex = 0
        for (item in items) {
            val itemString = StringBuilder()

            itemString.append(if (curIndex == selectedItemIndex) {
                ">"
            } else {
                " "
            })
            itemString.append("${item.title}:")
            if (state.getPlayer().isEquipped(item)) {
                itemString.append(" [equipped]")
            }
            if (item.hp > 0) itemString.append(" hp ${item.hp}")
            if (item.armor > 0) itemString.append(" armor ${item.armor}")
            if (item.attack > 0) itemString.append(" attack ${item.attack}")
            layer.putText(itemString.toString(), Position(0, curIndex * 2 + 1))
            curIndex++
        }
    }
}