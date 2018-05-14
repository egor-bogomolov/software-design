package ru.spbau.bogomolov.executor

import ru.spbau.bogomolov.environment.Environment
import ru.spbau.bogomolov.parser.BashLikeParser

/**
 * Executor specifically for the bash-like language described in README.md
 */
class BashLikeExecutor(private val env: Environment) : CommandLineExecutor {
    override fun processString(input: String): ExecutionResult {
        val parser = BashLikeParser(env)
        val roots = parser.parse(input)
        if (roots.isEmpty()) {
            return ExecutionResult(false, "", "")
        }
        var lastRoot = roots.first()
        roots.first().invoke()
        roots.subList(1, roots.size).forEach { root ->
            root.invoke(lastRoot.getOutput())
            lastRoot = root
        }
        return ExecutionResult(lastRoot.shouldExit(), lastRoot.getOutput(), lastRoot.getErrors())
    }
}