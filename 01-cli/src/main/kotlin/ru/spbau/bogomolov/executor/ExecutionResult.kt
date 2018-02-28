package ru.spbau.bogomolov.executor


/**
 * Result of execution.
 * ShouldExit is set to true when program tried to exit.
 * Output contains general output of execution.
 * Error contains text with errors encountered during execution.
 */
data class ExecutionResult(val shouldExit: Boolean, val output: String, val error: String)
