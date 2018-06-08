package controller.screens

/**
 * Current active screen.
 */
sealed class ActiveScreen

object GameScreen : ActiveScreen()

object LostScreen : ActiveScreen()

object InventoryScreen : ActiveScreen()

object Finished : ActiveScreen()

data class InvocationResult(val activeScreen: ActiveScreen, val hasChanged: Boolean)