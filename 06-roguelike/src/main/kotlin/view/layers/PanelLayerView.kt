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
import kotlin.math.min

/**
 * LayerView representing bottom panel with stats and buttons.
 */
class PanelLayerView(
        override val terminal: Terminal,
        override val size: Size,
        override val offset: Position,
        private val numberOfDisplayedEvents: Int
): LayerView {

    companion object {
        fun getHeight(numberOfDisplayedEvents: Int) = numberOfDisplayedEvents + 5
    }

    private val eventsFirstRow = 1
    private val statsRow = eventsFirstRow + numberOfDisplayedEvents + 1
    private val buttonsRow = statsRow + 1

    private var mode: Mode = GameMode
    private var layer: Layer? = null

    /**
     * Set current active screen.
     */
    fun setMode(mode: Mode) {
        this.mode = mode
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

        drawLoggedEvents(state, newLayer)
        drawPlayerInfo(state, newLayer)
        drawButtons(newLayer)

        terminal.pushLayer(newLayer)
        terminal.flush()

        layer = newLayer
    }

    /**
     * Draw last events in the log.
     */
    private fun drawLoggedEvents(state: GameState, layer: Layer) {
        val events = state.getLog()
        val numEvents = min(numberOfDisplayedEvents, events.size)
        layer.setForegroundColor(ANSITextColor.YELLOW)
        for (i in 0 until numEvents) {
            layer.putText(events[events.size - i - 1], Position.of(1, eventsFirstRow + i))
        }
        layer.resetColorsAndModifiers()
    }

    /**
     * Draw players stats.
     */
    private fun drawPlayerInfo(state: GameState, layer: Layer) {
        layer.putText("HP: ${state.getPlayer().getHp()}/${state.getPlayer().getMaxHp()}", Position.of(0, statsRow))
        layer.putText("Armor: ${state.getPlayer().getArmor()}", Position.of(size.columns / 3, statsRow))
        layer.putText("Attack:${state.getPlayer().getAttack()}", Position.of(size.columns / 3 * 2, statsRow))
    }

    /**
     * Draw buttons.
     */
    private fun drawButtons(layer: Layer) {
        layer.putText(mode.optionText, Position.of(size.columns / 3, buttonsRow))
        layer.putText("[Q]uit", Position.of(size.columns / 3 * 2, buttonsRow))
    }

}