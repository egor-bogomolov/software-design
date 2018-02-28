package ru.spbau.bogomolov.ast.commands

fun parsePwdFromString(string: String): Pwd? =
        if (string == "pwd" || string.startsWith("pwd ")) Pwd()
        else null


class Pwd : Command {

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