package view.layers

import model.GameState
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.terminal.Terminal

/**
 * Represents a layer that can be drawn in Terminal.
 */
interface LayerView {
    /**
     * Terminal in which all the drawing happens.
     */
    val terminal: Terminal

    /**
     * Size of layer in the terminal.
     */
    val size: Size

    /**
     * Position of top-left corner of the layer.
     */
    val offset: Position

    /**
     * Receive current game state and draw its representation.
     */
    fun draw(state: GameState)

    /**
     * Remove everything that has been drawn.
     */
    fun clear()
}