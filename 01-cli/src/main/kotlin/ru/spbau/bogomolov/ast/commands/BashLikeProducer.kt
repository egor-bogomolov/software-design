package ru.spbau.bogomolov.ast.commands

import ru.spbau.bogomolov.ast.AstNode

/**
 * Implementation of CommandProducer for bash-like language.
 */
class BashLikeProducer : CommandProducer {

    private val registeredCommandParsers = mutableListOf<(List<String>, List<AstNode>?) -> Command?>()

    override fun registerCommandParser(parseCommand: (List<String>, List<AstNode>?) -> Command?) {
        registeredCommandParsers.add(parseCommand)
    }

    override fun parseFromTokens(input: List<String>, inputNodes: List<AstNode>?): Command? {
        registeredCommandParsers.forEach { parseCommand ->
            parseCommand(input, inputNodes)?.let {
                return it
            }
        }
        return null
    }
}