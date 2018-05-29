package view.layers

import model.GameState
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.terminal.Terminal

interface LayerView {
    val terminal: Terminal
    val size: Size
    val offset: Position

    fun draw(state: GameState)
    fun clear()
}