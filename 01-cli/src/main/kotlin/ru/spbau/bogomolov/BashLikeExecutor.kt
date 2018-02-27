package ru.spbau.bogomolov

class BashLikeExecutor() : CommandLineInterpretator {
    override fun processString(string: String): InterpretationResult {
        return InterpretationResult(false, string, "No errors")
    }
}