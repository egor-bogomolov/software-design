package ru.spbau.bogomolov.ast.commands

/**
 * Allows to register parsers and then use them to turn strings into commands represented by AST nodes.
 */
interface CommandProducer {
    /**
     * Registers parser for a command. It should take string as an argument and return node representing a command
     * if it is possible to parse string, otherwise null.
     */
    fun registerCommandParser(parseCommand: (String) -> Command?)

    /**
     * If string represents a command (probably with arguments) and command's parser is registered then a node
     * representing the command is returned, otherwise null.
     */
    fun parseFromString(input: String): Command?
}