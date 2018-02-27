package ru.spbau.bogomolov

/**
 * Takes string that should be interpreted and returns results of interpretation
 */
interface CommandLineInterpretator {
    fun processString(string: String): InterpretationResult
}

/**
 * Result of execution.
 * ShouldExit is set to true when program tried to exit.
 * Output contains general output of execution.
 * Error contains text with errors encountered during execution.
 */
data class InterpretationResult(val shouldExit: Boolean, val output: String, val error: String)