package controller.screens

import org.codetome.zircon.api.input.Input

internal interface ScreenController {
    fun accept(input: Input): InvokationResult
    fun draw()
}