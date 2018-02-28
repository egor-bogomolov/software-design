package ru.spbau.bogomolov.executor

import ru.spbau.bogomolov.parser.BashLikeParser

class BashLikeExecutor : CommandLineExecutor {
    override fun processString(input: String): ExecutionResult {
        val parser = BashLikeParser()
        val root = parser.parse(input)
        root.invoke()
        return ExecutionResult(false, root.getOutput(), root.getErrors())
    }
}