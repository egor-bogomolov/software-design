package ru.spbau.bogomolov.ast.commands

import ru.spbau.bogomolov.ast.AstNode

/**
 * If first word in string is 'exit' then parsing is successful.
 */
fun parseEchoFromString(string: String): Echo? {
    val words = string.toWords()
    if (words.isEmpty() || words[0] != "echo") {
        return null
    }
    return Echo(words.subList(1, words.size).toTextNodes())
}

/**
 * echo command. Prints arguments, one per line. If input is provided then arguments are ignored and input is printed.
 */
class Echo(args: List<AstNode>) : CommandWithArguments(args, "echo") {
    override fun shouldExit() = false

    override fun consumeInput(input: String) {
        appendToOutput(input + "\n")
    }

    override fun consumeArgument(arg: String) {
        print(arg)
        appendToOutput(arg + "\n")
    }
}