package ru.spbau.bogomolov

fun main(args : Array<String>): Unit {
    val appInstance = CommandLineExecutor()
    val executor = BashLikeExecutor()
    appInstance.run(executor)
}