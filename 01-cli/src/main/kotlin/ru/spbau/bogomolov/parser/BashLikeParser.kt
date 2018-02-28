package ru.spbau.bogomolov.parser

import ru.spbau.bogomolov.ast.AstNode
import ru.spbau.bogomolov.ast.commands.*
import ru.spbau.bogomolov.ast.utilitynodes.TextNode

class BashLikeParser : CommandLineParser {
    private fun registerCommands(commandProducer: BashLikeProducer) {
        commandProducer.registerCommandParser { parsePwdFromString(it) }
        commandProducer.registerCommandParser { parseEchoFromString(it) }
        commandProducer.registerCommandParser { parseCatFromString(it) }
        commandProducer.registerCommandParser { parseWcFromString(it) }
        commandProducer.registerCommandParser { parseExitFromString(it) }
    }

    override fun parse(input: String): AstNode {
        val commandProducer = BashLikeProducer()
        registerCommands(commandProducer)
        return commandProducer.parseFromString(input) ?: TextNode(input)
    }
}