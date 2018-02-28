package ru.spbau.bogomolov.ast.commands

import ru.spbau.bogomolov.environment.Environment

fun parseAssignmentFromString(env: Environment, string: String): Assignment? {
    val trimmedString = string.trim()
    val endOfWord = trimmedString.indexOfFirst { !(it in 'a'..'z' || it in 'A'..'Z' || it in '0'..'9') }
    val firstAssignment = trimmedString.indexOfFirst { it == '=' }
    if (firstAssignment == -1 || firstAssignment > endOfWord || firstAssignment == 0) return null
    return Assignment(
            env,
            trimmedString.substring(0, firstAssignment),
            trimmedString.substring(firstAssignment + 1)
    )
}

class Assignment(private val env: Environment, private val name: String, private val value: String) : Command {
    override fun shouldExit() = false

    override fun invoke() {
        env.setValue(name, value)
    }

    override fun invoke(input: String) {
        invoke()
    }

    override fun getOutput() = ""

    override fun getErrors() = ""
}