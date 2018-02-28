package ru.spbau.bogomolov.executor

import ru.spbau.bogomolov.environment.Environment
import ru.spbau.bogomolov.parser.BashLikeParser

class BashLikeExecutor(private val env: Environment) : CommandLineExecutor {
    override fun processString(input: String): ExecutionResult {
        val parser = BashLikeParser(env)
        val root = parser.parse(input)
        root.invoke()
        return ExecutionResult(root.shouldExit(), root.getOutput(), root.getErrors())
    }
}