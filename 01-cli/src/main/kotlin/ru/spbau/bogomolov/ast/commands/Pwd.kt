package ru.spbau.bogomolov.ast.commands

/**
 * If first word in string is 'pwd' then parsing is successful.
 */
fun parsePwdFromString(string: String): Pwd? {
    val words = string.toWords()
    if (words.isEmpty() || words[0] != "pwd") {
        return null
    }
    return Pwd()
}

/**
 * pwd command. Doesn't take arguments. Prints path to current directory.
 */
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