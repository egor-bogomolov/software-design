package ru.spbau.bogomolov.ast.commands

import ru.spbau.bogomolov.ast.AstNode

fun parseEchoFromString(string: String): Echo? {
    val words = string.toWords()
    if (words.isEmpty() || words[0] != "echo") {
        return null
    }
    return Echo(words.subList(1, words.size).toTextNodes())
}

class Echo(args: List<AstNode>) : CommandWithArguments(args, "echo") {
    override fun shouldExit() = false

    override fun consumeInput(input: String) {
        appendToOutput(input + "\n")
    }

    override fun consumeArgument(arg: String) {
        appendToOutput(arg + "\n")
    }
}