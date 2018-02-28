package ru.spbau.bogomolov.ast.commands

interface CommandProducer {
    fun registerCommandParser(parseCommand: (String) -> Command?)
    fun parseFromString(input: String): Command?
}