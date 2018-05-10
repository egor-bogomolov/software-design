package ru.spbau.bogomolov.parser

import ru.spbau.bogomolov.ast.AstNode
import ru.spbau.bogomolov.ast.commands.*
import ru.spbau.bogomolov.ast.utilitynodes.ExecuteNode
import ru.spbau.bogomolov.environment.Environment
import ru.spbau.bogomolov.parser.preprocessor.BashLikePreprocessor

/**
 * Parser for bash-like language.
 */
class BashLikeParser(private val env: Environment) : CommandLineParser {
    private fun registerCommands(commandProducer: CommandProducer) {
        commandProducer.registerCommandParser { parseAssignmentFromString(env, it) }
        commandProducer.registerCommandParser { parsePwdFromString(it) }
        commandProducer.registerCommandParser { parseEchoFromString(it) }
        commandProducer.registerCommandParser { parseCatFromString(it) }
        commandProducer.registerCommandParser { parseWcFromString(it) }
        commandProducer.registerCommandParser { parseExitFromString(it) }
    }

    /**
     * Transforms text into list of AST nodes that correspond to different commands separated with pipes.
     */
    override fun parse(input: String): List<AstNode> {
        val preprocessor = BashLikePreprocessor(env)
        val commandProducer = BashLikeProducer()
        registerCommands(commandProducer)
        return preprocessor.process(input).map { commandProducer.parseFromString(it) ?: ExecuteNode(it) }
    }
}