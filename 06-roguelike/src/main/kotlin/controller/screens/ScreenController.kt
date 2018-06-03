package controller.screens

import org.codetome.zircon.api.input.Input

/**
 * Represents controller of game screen.
 */
internal interface ScreenController {
    /**
     * Receive input from view.
     */
    fun accept(input: Input): InvocationResult

    /**
     * Trigger drawing on layers corresponding to this controller.
     */
    fun draw()
}