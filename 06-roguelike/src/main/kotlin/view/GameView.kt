package view

import model.GameState
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.TerminalBuilder
import org.codetome.zircon.api.resource.CP437TilesetResource

class GameView(
        val mapVisibleHeight: Int,
        val mapVisibleWidth: Int,
        val state: GameState
) {

    companion object {
        private const val GAME_TITLE = "Awesome Roguelike"

        fun createGameView(mapVisibleHeight: Int, mapVisibleWidth: Int, state: GameState) =
                GameView(mapVisibleHeight, mapVisibleWidth, state)
    }

    private val terminal = TerminalBuilder
            .newBuilder()
            .initialTerminalSize(Size.of(mapVisibleWidth, mapVisibleHeight))
            .font(CP437TilesetResource.WANDERLUST_16X16.toFont())
            .title(GAME_TITLE)
            .build()

    fun refresh() {
        for (col in 0 until mapVisibleWidth) {
            for (row in 0 until mapVisibleHeight) {
                terminal.putCharacter(state.map.getObjectAt(row, col).asCharacter())
            }
        }
    }

}