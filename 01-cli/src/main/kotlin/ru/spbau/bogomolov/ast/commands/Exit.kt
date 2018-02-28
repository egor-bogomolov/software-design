package ru.spbau.bogomolov.ast.commands

fun parseExitFromString(string: String): Exit? {
    val words = string.toWords()
    if (words.isEmpty() || words[0] != "exit") {
        return null
    }
    return Exit()
}

class Exit : Command {
    override fun shouldExit() = true

    private var output: String = ""
    private var errors: String = ""

    override fun invoke() {}

    override fun invoke(input: String) {}

    override fun getOutput(): String = output

    override fun getErrors(): String = errors
}