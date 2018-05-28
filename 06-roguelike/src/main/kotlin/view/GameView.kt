package view

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.TerminalBuilder
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.resource.CP437TilesetResource
import view.layers.LayerView
import view.layers.LostGameLayerView
import view.layers.MapLayerView
import view.layers.PanelLayerView
import java.util.function.Consumer

class GameView(
        val mapVisibleHeight: Int,
        val mapVisibleWidth: Int
) {

    interface Listener {
        fun onInputAction(input: Input)
    }

    companion object {
        private const val GAME_TITLE = "Awesome Roguelike"
        private const val PANEL_ROWS = 4

        fun createGameView(mapVisibleHeight: Int, mapVisibleWidth: Int) =
                GameView(mapVisibleHeight, mapVisibleWidth)
    }

    private val terminal = TerminalBuilder
            .newBuilder()
            .initialTerminalSize(Size.of(mapVisibleWidth, mapVisibleHeight + PANEL_ROWS))
            .font(CP437TilesetResource.WANDERLUST_16X16.toFont())
            .title(GAME_TITLE)
            .build()

    private val listeners = mutableListOf<Listener>()
    private val mapLayer =
            MapLayerView(terminal, Size(mapVisibleWidth, mapVisibleHeight), Position.TOP_LEFT_CORNER)
    private val panelLayer =
            PanelLayerView(terminal, Size(mapVisibleWidth, PANEL_ROWS), Position.of(0, mapVisibleHeight))
    private val lostGameLayer =
            LostGameLayerView(terminal, Size(mapVisibleWidth, mapVisibleHeight), Position.TOP_LEFT_CORNER)

    init {
        terminal.onInput(Consumer { input ->
            listeners.forEach { it.onInputAction(input) }
        })
    }

    fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    fun getMapLayer(): LayerView = mapLayer

    fun getPanelLayer(): LayerView = panelLayer

    fun getLostGameLayer(): LayerView = lostGameLayer

    fun finish() {
        terminal.close()
    }
}