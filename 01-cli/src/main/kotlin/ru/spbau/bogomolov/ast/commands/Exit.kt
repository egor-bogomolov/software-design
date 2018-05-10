package ru.spbau.bogomolov.ast.commands

/**
 * If first word in string is 'exit' then parsing is successful.
 */
fun parseExitFromString(string: String): Exit? {
    val words = string.toWords()
    if (words.isEmpty() || words[0] != "exit") {
        return null
    }
    return Exit()
}

/**
 * exit command. Doesn't take arguments. Attempts to finish execution.
 */
class Exit : Command {
    override fun shouldExit() = true

    private var output: String = ""
    private var errors: String = ""

    override fun invoke() {}

    override fun invoke(input: String) {}

    override fun getOutput(): String = output

    override fun getErrors(): String = errors
}