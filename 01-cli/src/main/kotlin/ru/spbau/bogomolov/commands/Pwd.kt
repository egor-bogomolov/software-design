package ru.spbau.bogomolov.commands

fun parsePwdFromString(string: String): Pwd? =
        if (string == "pwd" || string.startsWith("pwd ")) Pwd()
        else null


class Pwd : Command {
    override fun invoke() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOutput(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getErrors(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}