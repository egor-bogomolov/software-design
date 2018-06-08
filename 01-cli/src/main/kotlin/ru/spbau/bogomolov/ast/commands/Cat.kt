package ru.spbau.bogomolov.ast.commands

import ru.spbau.bogomolov.ast.AstNode
import java.io.File

/**
 * If first token is 'cat' then parsing is successful, other tokens are treated as arguments.
 * If inputNodes are provided then they replace arguments.
 */
fun parseCatFromTokens(tokens: List<String>, inputNodes: List<AstNode>?): Cat? {
    if (tokens.isEmpty() || tokens[0] != "cat") {
        return null
    }
    inputNodes?.let { return Cat(inputNodes) }
    return Cat(tokens.subList(1, tokens.size).toTextNodes(true))
}

/**
 * echo command. Arguments are treated as names of files and their content is printed. If input-arg is provided then
 * its output printed.
 */
class Cat(args: List<AstNode>) : Command(args, "cat", true, false) {

    override fun consumeArgument(arg: AstNode) {
        val input = arg.getOutput()
        if (arg.isArgument()) {
            appendToOutput(File(input).inputStream().bufferedReader().use { it.readText() } + "\n")
        } else {
            appendToOutput(input + "\n")
        }
    }

    override fun shouldExit() = false

}