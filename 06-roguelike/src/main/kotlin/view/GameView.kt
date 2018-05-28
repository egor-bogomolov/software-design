package view

import model.GameState
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.TerminalBuilder
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.resource.CP437TilesetResource
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

        fun createGameView(mapVisibleHeight: Int, mapVisibleWidth: Int) =
                GameView(mapVisibleHeight, mapVisibleWidth)
    }

    private val terminal = TerminalBuilder
            .newBuilder()
            .initialTerminalSize(Size.of(mapVisibleWidth, mapVisibleHeight))
            .font(CP437TilesetResource.WANDERLUST_16X16.toFont())
            .title(GAME_TITLE)
            .build()

    private val listeners = mutableListOf<Listener>()

    init {
        terminal.onInput(Consumer { input ->
            listeners.forEach { it.onInputAction(input) }
        })
    }

    fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    fun draw(state: GameState) {
        for (col in 0 until mapVisibleWidth) {
            for (row in 0 until mapVisibleHeight) {
                terminal.putCharacter(state.map.getObjectAt(row, col).asCharacter())
            }
        }
    }

}