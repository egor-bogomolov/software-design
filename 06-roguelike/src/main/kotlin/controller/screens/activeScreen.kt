package controller.screens

sealed class ActiveScreen

object GameScreen : ActiveScreen()

object LostScreen : ActiveScreen()

object InventoryScreen : ActiveScreen()

object Finished : ActiveScreen()

data class InvocationResult(val activeScreen: ActiveScreen, val hasChanged: Boolean)