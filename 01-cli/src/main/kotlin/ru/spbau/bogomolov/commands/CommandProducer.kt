package ru.spbau.bogomolov.commands

interface CommandProducer {
    fun parseFromString(string: String): Command?
}