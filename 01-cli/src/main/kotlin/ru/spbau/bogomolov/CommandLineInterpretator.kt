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

            if (result.output.isNotEmpty()) {
                println(result.output)
            }
            if (result.error.isNotEmpty()) {
                System.err.println(result.error)
            }
        }
    }
}
