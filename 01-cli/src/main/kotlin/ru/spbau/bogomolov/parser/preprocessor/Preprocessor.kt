package ru.spbau.bogomolov.parser.preprocessor

interface Preprocessor {
    fun process(input: String): List<String>
}