package ru.spbau.bogomolov

/**
 * Wrapper around ... using console input and output.
 */
class CommandLineInterface {
    fun run(executor: CommandLineInterpretator) {
        while(true) {
            val line = readLine() ?: break
            println(line)
        }
    }
}