package ru.spbau.bogomolov

/**
 * Takes string that should be interpreted and returns results of interpretation
 */
interface CommandLineExecutor {
    fun processString(input: String): ExecutionResult
}
