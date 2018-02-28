package ru.spbau.bogomolov.ast.commands

interface CommandProducer {
    fun parseFromString(input: String): Command?
}