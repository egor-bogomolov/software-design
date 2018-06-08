package ru.spbau.bogomolov.ast.commands

import ru.spbau.bogomolov.ast.AstNode

/**
 * Allows to register parsers and then use them to turn strings into commands represented by AST nodes.
 */
interface CommandProducer {
    /**
     * Registers parser for a command. It should take list of tokens (strings) as an argument and return node
     * representing a command if it is possible to parse tokens, otherwise null.
     */
    fun registerCommandParser(parseCommand: (List<String>, List<AstNode>?) -> Command?)

    /**
     * If tokens represent a command (probably with arguments) and command's parser is registered then a node
     * representing the command is returned, otherwise null.
     */
    fun parseFromTokens(input: List<String>, inputNodes: List<AstNode>?): Command?
}