package ru.spbau.bogomolov.executor

import ru.spbau.bogomolov.environment.Environment
import ru.spbau.bogomolov.parser.BashLikeParser

/**
 * Executor specifically for the bash-like language described in README.md
 */
class BashLikeExecutor(private val env: Environment) : CommandLineExecutor {
    override fun processString(input: String): ExecutionResult {
        val parser = BashLikeParser(env)
        val commands = parser.parse(input)
        if (commands.isEmpty()) {
            return ExecutionResult(false, "", "")
        }
        var lastCommand = commands.first()
        commands.first().invoke()
        commands.subList(1, commands.size).forEach { command ->
            command.invoke(lastCommand.getOutput())
            lastCommand = command
        }
        return ExecutionResult(lastCommand.shouldExit(), lastCommand.getOutput(), lastCommand.getErrors())
    }
}