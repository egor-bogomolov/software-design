package view.layers

import model.GameState
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.terminal.Terminal

internal class LostGameLayerView(
        override val terminal: Terminal,
        override val size: Size,
        override val offset: Position
) : LayerView {
    override fun draw(state: GameState) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}