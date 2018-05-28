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



internal class PanelLayerView(
        override val terminal: Terminal,
        override val size: Size,
        override val offset: Position
): LayerView {

    override fun draw(state: GameState) {
        val layer1 = LayerBuilder.newBuilder()
                .textImage(TextImageBuilder.newBuilder()
                        .filler(TextCharacterBuilder.newBuilder()
                                .foregroundColor(ANSITextColor.RED)
                                .backgroundColor(TextColorFactory.TRANSPARENT)
                                .character('+')
                                .build())
                        .size(size)
                        .build())
                .offset(offset)
                .build()

        terminal.pushLayer(layer1)

        terminal.flush()

    }

}