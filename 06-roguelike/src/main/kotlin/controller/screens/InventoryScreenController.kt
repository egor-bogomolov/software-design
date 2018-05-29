package controller.screens

import view.layers.InventoryMode
import model.GameState
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.InputType
import view.GameView
import kotlin.math.max
import kotlin.math.min

class InventoryScreenController(
        val view: GameView,
        val state: GameState
) : ScreenController {

    private var selectedItemIndex = 0

    override fun accept(input: Input): InvokationResult {
        if (input.isKeyStroke()) {
            val stroke = input.asKeyStroke()
            when(stroke.getInputType()) {
                InputType.ArrowDown -> {
                    selectedItemIndex = min(state.getPlayer().getItems().size - 1, selectedItemIndex + 1)
                    return InvokationResult(InventoryScreen, true)
                }
                InputType.ArrowUp -> {
                    selectedItemIndex = max(0, selectedItemIndex - 1)
                    return InvokationResult(InventoryScreen, true)
                }
                InputType.ArrowRight -> {
                    state.getPlayer().equip(selectedItemIndex)
                    return InvokationResult(InventoryScreen, true)
                }
                InputType.Character -> when(stroke.getCharacter()) {
                    'R', 'r' -> {
                        selectedItemIndex = 0
                        return InvokationResult(GameScreen, true)
                    }
                    'Q', 'q' -> {
                        return InvokationResult(Finished, true)
                    }
                    else -> {}
                }
                else -> {}
            }
        }
        return InvokationResult(InventoryScreen, false)
    }

    override fun draw() {
        view.getInventoryLayer().setSelectedItem(selectedItemIndex)
        view.getInventoryLayer().draw(state)
        view.getPanelLayer().setMode(InventoryMode)
        view.getPanelLayer().draw(state)
    }
}