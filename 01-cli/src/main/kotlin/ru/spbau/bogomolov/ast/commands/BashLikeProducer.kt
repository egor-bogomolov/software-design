package ru.spbau.bogomolov.ast.commands

class BashLikeProducer : CommandProducer {

    private val registeredCommandParsers = mutableListOf<(String) -> Command?>()

    fun registerCommandParser(parseCommand: (String) -> Command?) {
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