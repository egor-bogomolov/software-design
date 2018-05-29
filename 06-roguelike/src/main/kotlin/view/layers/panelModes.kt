package view.layers

sealed class Mode(val optionText: String)

object GameMode : Mode("[I]nventory")

object InventoryMode : Mode("[R]eturn")