package ru.spbau.bogomolov

import ru.spbau.bogomolov.environment.LocalMapEnvironment
import ru.spbau.bogomolov.executor.BashLikeExecutor

fun main(args : Array<String>) {
    val appInstance = CommandLineInterpretator()
    val executor = BashLikeExecutor(LocalMapEnvironment())
    appInstance.run(executor)
}