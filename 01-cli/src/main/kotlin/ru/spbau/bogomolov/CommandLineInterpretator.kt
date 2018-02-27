package ru.spbau.bogomolov

interface CommandLineInterpretator {
    fun processString(string: String): InterpretationResult {
        return InterpretationResult(true, "")
    }

    data class InterpretationResult(val shouldExit: Boolean, val output: String)
}
