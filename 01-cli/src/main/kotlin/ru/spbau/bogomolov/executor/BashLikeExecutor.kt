package ru.spbau.bogomolov.executor

import ru.spbau.bogomolov.environment.Environment
import ru.spbau.bogomolov.parser.BashLikeParser
import ru.spbau.bogomolov.parser.CommandLineParser

/**
 * Executor specifically for the bash-like language described in README.md
 */
class BashLikeExecutor(private val env: Environment) : CommandLineExecutor {

    override fun processString(input: String) = processString(input, BashLikeParser(env))

    internal fun processString(input: String, parser: CommandLineParser): ExecutionResult {
        val root = parser.parse(input) ?: return ExecutionResult(false, "", "")
        root.invoke()
        return ExecutionResult(root.shouldExit(), root.getOutput(), root.getErrors())
    }
}