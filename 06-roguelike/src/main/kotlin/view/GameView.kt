package view

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.TerminalBuilder
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.resource.CP437TilesetResource
import view.layers.*
import java.util.function.Consumer

/**
 * Draws representation of Model with usage of zircon library.
 * View contains several layers that can be received via functions.
 * Each layer can draw itself on top of the screen.
 */
class GameView private constructor(
        val mapVisibleHeight: Int,
        val mapVisibleWidth: Int
) {

    /**
     * Listens to input actions happenning in the view.
     */
    interface Listener {
        fun onInputAction(input: Input)
    }

    companion object {
        private const val GAME_TITLE = "Awesome Roguelike"
        private const val PANEL_ROWS = 4

        /**
         * Used instead of constructor.
         */
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
    private val inventoryLayer =
            InventoryLayerView(terminal, Size(mapVisibleWidth, mapVisibleHeight), Position.TOP_LEFT_CORNER)

    init {
        terminal.onInput(Consumer { input ->
            listeners.forEach { it.onInputAction(input) }
        })
    }

    /**
     * Add listener to the list of listeners that will be triggered on input action.
     */
    fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    /**
     * Receive map layer contained in the view.
     */
    fun getMapLayer(): LayerView = mapLayer

    /**
     * Receive panel layer contained in the view.
     */
    fun getPanelLayer(): PanelLayerView = panelLayer

    /**
     * Receive lost game layer contained in the view.
     */
    fun getLostGameLayer(): LayerView = lostGameLayer

    /**
     * Receive inventory layer contained in the view.
     */
    fun getInventoryLayer(): InventoryLayerView = inventoryLayer

    /**
     * Shutdown the view.
     */
    fun finish() {
        terminal.close()
    }

    /**
     * Clear all contained layers.
     */
    fun clearAll() {
        mapLayer.clear()
        panelLayer.clear()
        lostGameLayer.clear()
        inventoryLayer.clear()
    }
}