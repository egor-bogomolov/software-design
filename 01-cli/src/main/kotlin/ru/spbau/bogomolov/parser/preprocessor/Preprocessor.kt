package ru.spbau.bogomolov.parser.preprocessor

/**
 * Splits string into tokens.
 */
interface Preprocessor {
    fun process(input: String): List<String>
}