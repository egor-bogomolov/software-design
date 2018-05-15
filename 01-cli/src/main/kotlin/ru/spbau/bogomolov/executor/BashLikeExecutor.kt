package ru.spbau.bogomolov.executor

import ru.spbau.bogomolov.environment.Environment
import ru.spbau.bogomolov.parser.BashLikeParser

/**
 * Executor specifically for the bash-like language described in README.md
 */
class BashLikeExecutor(private val env: Environment) : CommandLineExecutor {
    override fun processString(input: String): ExecutionResult {
        val parser = BashLikeParser(env)
        val root = parser.parse(input) ?: return ExecutionResult(false, "", "")
        root.invoke()
        return ExecutionResult(root.shouldExit(), root.getOutput(), root.getErrors())
    }
}