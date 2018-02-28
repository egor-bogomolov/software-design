package ru.spbau.bogomolov.parser

import ru.spbau.bogomolov.ast.AstNode
import ru.spbau.bogomolov.ast.commands.*
import ru.spbau.bogomolov.ast.utilitynodes.ExecuteNode
import ru.spbau.bogomolov.environment.Environment

class BashLikeParser(private val env: Environment) : CommandLineParser {
    private fun registerCommands(commandProducer: CommandProducer) {
        commandProducer.registerCommandParser { parseAssignmentFromString(env, it) }
        commandProducer.registerCommandParser { parsePwdFromString(it) }
        commandProducer.registerCommandParser { parseEchoFromString(it) }
        commandProducer.registerCommandParser { parseCatFromString(it) }
        commandProducer.registerCommandParser { parseWcFromString(it) }
        commandProducer.registerCommandParser { parseExitFromString(it) }
    }

    override fun parse(input: String): AstNode {
        val commandProducer = BashLikeProducer()
        registerCommands(commandProducer)

        return commandProducer.parseFromString(input) ?: ExecuteNode(input)
    }
}