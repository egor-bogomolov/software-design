package ru.spbau.bogomolov

fun main(args : Array<String>) {
    val appInstance = CommandLineInterpretator()
    val interpretator = BashLikeExecutor()
    appInstance.run(interpretator)
}