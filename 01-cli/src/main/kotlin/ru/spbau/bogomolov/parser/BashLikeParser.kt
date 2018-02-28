package ru.spbau.bogomolov.parser

import ru.spbau.bogomolov.ast.AstNode
import ru.spbau.bogomolov.ast.commands.BashLikeProducer
import ru.spbau.bogomolov.ast.commands.parsePwdFromString
import ru.spbau.bogomolov.ast.utilitynodes.TextNode

class BashLikeParser : CommandLineParser {
    override fun parse(input: String): AstNode {
        val commandProducer = BashLikeProducer()
        commandProducer.registerCommandParser { parsePwdFromString(it) }
        return commandProducer.parseFromString(input) ?: TextNode(input)
    }
}