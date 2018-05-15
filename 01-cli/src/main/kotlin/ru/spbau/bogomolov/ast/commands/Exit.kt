package ru.spbau.bogomolov.ast.commands

import ru.spbau.bogomolov.ast.AstNode

/**
 * If first token is 'exit' then parsing is successful.
 */
fun parseExitFromTokens(tokens: List<String>): Exit? {
    if (tokens.isEmpty() || tokens[0] != "exit") {
        return null
    }
    return Exit()
}

/**
 * exit command. Doesn't take arguments. Attempts to finish execution.
 */
class Exit : Command(emptyList(), "exit", false, false) {

    override fun consumeArgument(arg: AstNode) {}

    override fun shouldExit() = true
}