package ru.spbau.bogomolov

import ru.spbau.bogomolov.executor.CommandLineExecutor

/**
 * Wrapper around an executor implementing console app interface.
 */
class CommandLineInterpretator {
    fun run(executor: CommandLineExecutor) {
        while(true) {
            val line = readLine() ?: break
            val result = executor.processString(line)
            if (result.shouldExit) break
            println(result.output)
            System.err.println(result.error)
        }
    }
}
