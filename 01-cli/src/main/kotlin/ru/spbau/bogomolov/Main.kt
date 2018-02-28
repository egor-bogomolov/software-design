package ru.spbau.bogomolov

import ru.spbau.bogomolov.executor.BashLikeExecutor

fun main(args : Array<String>) {
    val appInstance = CommandLineInterpretator()
    val interpretator = BashLikeExecutor()
    appInstance.run(interpretator)
}