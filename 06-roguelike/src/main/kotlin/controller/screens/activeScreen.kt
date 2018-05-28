package controller.screens

sealed class ActiveScreen

object GameScreen : ActiveScreen()

object LostScreen : ActiveScreen()

object Finished : ActiveScreen()