package ru.spbau.bogomolov

import ru.spbau.bogomolov.environment.LocalMapEnvironment
import ru.spbau.bogomolov.executor.BashLikeExecutor

/**
 * Starts a console application that can execute commands in a bash-like language described in README.md
 */
fun main(args : Array<String>) {
    val appInstance = CommandLineInterpretator()
    val executor = BashLikeExecutor(LocalMapEnvironment())
    appInstance.run(executor)
}