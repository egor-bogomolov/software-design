package ru.spbau.bogomolov.executor

/**
 * Takes a string and executes it according to language rules.
 */
interface CommandLineExecutor {
    fun processString(input: String): ExecutionResult
}
