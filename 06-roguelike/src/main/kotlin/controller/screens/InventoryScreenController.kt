package controller.screens

import view.layers.InventoryMode
import model.GameState
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.InputType
import view.GameView
import kotlin.math.max
import kotlin.math.min

/**
 * ScreenController for inventory screen.
 */
class InventoryScreenController(
        val view: GameView,
        val state: GameState
) : ScreenController {

    private var selectedItemIndex = 0

    /**
     * {@inheritDoc}
     */
    override fun accept(input: Input): InvocationResult {
        if (input.isKeyStroke()) {
            val stroke = input.asKeyStroke()
            when(stroke.getInputType()) {
                InputType.ArrowDown -> {
                    selectedItemIndex = min(state.getPlayer().getItems().size - 1, selectedItemIndex + 1)
                    return InvocationResult(InventoryScreen, true)
                }
                InputType.ArrowUp -> {
                    selectedItemIndex = max(0, selectedItemIndex - 1)
                    return InvocationResult(InventoryScreen, true)
                }
                InputType.ArrowRight -> {
                    state.getPlayer().equip(selectedItemIndex)
                    return InvocationResult(InventoryScreen, true)
                }
                InputType.Character -> when(stroke.getCharacter()) {
                    'R', 'r' -> {
                        selectedItemIndex = 0
                        return InvocationResult(GameScreen, true)
                    }
                    'Q', 'q' -> {
                        return InvocationResult(Finished, true)
                    }
                    else -> {}
                }
                else -> {}
            }
        }
        return InvocationResult(InventoryScreen, false)
    }

    /**
     * {@inheritDoc}
     */
    override fun draw() {
        view.getInventoryLayer().setSelectedItem(selectedItemIndex)
        view.getInventoryLayer().draw(state)
        view.getPanelLayer().setMode(InventoryMode)
        view.getPanelLayer().draw(state)
    }
}