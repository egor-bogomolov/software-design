package ru.spbau.bogomolov

import ru.spbau.bogomolov.executor.BashLikeExecutor

fun main(args : Array<String>) {
    val appInstance = CommandLineInterpretator()
    val executor = BashLikeExecutor()
    appInstance.run(executor)
}