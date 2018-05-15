package ru.spbau.bogomolov.ast.commands

import ru.spbau.bogomolov.ast.AstNode
import ru.spbau.bogomolov.environment.Environment

/**
 * If multiple tokens are provided then only first is used.
 * If token starts with latin letters and digits (without any space symbols) followed by '=' then parsing is
 * successful and everything before '=' sign is treated as name of a variable.
 */
fun parseAssignmentFromTokens(env: Environment, tokens: List<String>): Assignment? {
    if (tokens.isEmpty()) return null

    val string = tokens[0]
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

class Assignment(
        private val env: Environment,
        private val name: String,
        private val value: String
) : Command(emptyList(), "assignment", false, false) {

    override fun consumeArgument(arg: AstNode) {}

    override fun shouldExit() = false

    override fun invoke() {
        env.setValue(name, value)
    }
}