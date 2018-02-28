package ru.spbau.bogomolov.commands

class BashLikeProducer : CommandProducer {

    private val registeredCommandParsers = mutableListOf<(String) -> Command?>()

    fun registerCommandParser(parseCommand: (String) -> Command?) {
        registeredCommandParsers.add(parseCommand)
    }

    override fun parseFromString(string: String): Command? {
        registeredCommandParsers.forEach { parseCommand ->
            parseCommand(string)?.let {
                return it
            }
        }
        return null
    }
}