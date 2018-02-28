package ru.spbau.bogomolov.ast.commands

fun parsePwdFromString(string: String): Pwd? {
    val words = string.toWords()
    if (words.isEmpty() || words[0] != "pwd") {
        return null
    }
    return Pwd()
}

class Pwd : Command {
    override fun shouldExit() = false

    private var output: String = ""
    private var errors: String = ""

    override fun invoke() {
        output = System.getProperty("user.dir") + "\n"
    }

    override fun invoke(input: String) {
        invoke()
    }

    override fun getOutput(): String = output

    override fun getErrors(): String = errors
}