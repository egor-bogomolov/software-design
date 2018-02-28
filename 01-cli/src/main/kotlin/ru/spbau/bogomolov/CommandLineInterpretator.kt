package ru.spbau.bogomolov

/**
 * Wrapper around ... using console input and output.
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
