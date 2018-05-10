package ru.spbau.bogomolov.ast.commands

/**
 * Implementation of CommandProducer for bash-like language.
 */
class BashLikeProducer : CommandProducer {

    private val registeredCommandParsers = mutableListOf<(String) -> Command?>()

    override fun registerCommandParser(parseCommand: (String) -> Command?) {
        registeredCommandParsers.add(parseCommand)
    }

    override fun parseFromString(input: String): Command? {
        registeredCommandParsers.forEach { parseCommand ->
            parseCommand(input)?.let {
                return it
            }
        }
        return null
    }
}