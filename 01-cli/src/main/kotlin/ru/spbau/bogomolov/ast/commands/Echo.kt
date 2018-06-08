package ru.spbau.bogomolov.ast.commands

import ru.spbau.bogomolov.ast.AstNode

/**
 * If first token is 'echo' then parsing is successful, other tokens are treated as arguments.
 * If inputNodes are provided then they replace arguments.
 */
fun parseEchoFromTokens(tokens: List<String>, inputNodes: List<AstNode>?): Echo? {
    if (tokens.isEmpty() || tokens[0] != "echo") {
        return null
    }
    inputNodes?.let { return Echo(inputNodes) }
    return Echo(tokens.subList(1, tokens.size).toTextNodes(true))
}

/**
 * echo command. Prints arguments' output, one per line.
 */
class Echo(args: List<AstNode>) : Command(args, "echo", false, false) {

    override fun consumeArgument(arg: AstNode) {
        appendToOutput(arg.getOutput() + "\n")
    }

    override fun shouldExit() = false
}