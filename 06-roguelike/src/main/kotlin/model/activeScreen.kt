package model

sealed class ActiveScreen

object GameScreen : ActiveScreen()

object LostScreen : ActiveScreen()