package ru.spbau.bogomolov.parser

import ru.spbau.bogomolov.ast.AstNode
import ru.spbau.bogomolov.ast.commands.*
import ru.spbau.bogomolov.ast.utilitynodes.ExecuteNode
import ru.spbau.bogomolov.environment.Environment

/**
 * Parser for bash-like language.
 */
class BashLikeParser(private val env: Environment) : CommandLineParser {
    private fun registerCommands(commandProducer: CommandProducer) {
        commandProducer.registerCommandParser { tokens, _ ->
            parseAssignmentFromTokens(env, tokens)
        }
        commandProducer.registerCommandParser { tokens, _ ->
            parsePwdFromTokens(tokens)
        }
        commandProducer.registerCommandParser { tokens, inputNodes ->
            parseEchoFromTokens(tokens, inputNodes)
        }
        commandProducer.registerCommandParser { tokens, inputNodes ->
            parseCatFromTokens(tokens, inputNodes)
        }
        commandProducer.registerCommandParser { tokens, inputNodes ->
            parseWcFromTokens(tokens, inputNodes)
        }
        commandProducer.registerCommandParser { tokens, _ ->
            parseExitFromTokens(tokens)
        }
        commandProducer.registerCommandParser { tokens, inputNodes ->
            parseGrepFromTokens(tokens, inputNodes)
        }
    }

    /**
     * Transforms text into AST and returns node that correspond to the last command.
     */
    override fun parse(input: String): AstNode? {
        val commandProducer = BashLikeProducer()
        registerCommands(commandProducer)

        val tokenSets = splitTokensByPipes(splitIntoTokens(input))
        var topRoot: AstNode? = null
        for (tokenSet in tokenSets) {
            topRoot = if (topRoot == null) {
                commandProducer.parseFromTokens(tokenSet, null) ?: ExecuteNode(tokenSet)
            } else {
                commandProducer.parseFromTokens(tokenSet, listOf(topRoot)) ?: ExecuteNode(tokenSet)
            }
        }
        return topRoot
    }

    internal fun splitTokensByPipes(tokens: List<String>): List<List<String>> {
        val result = mutableListOf<List<String>>()
        var last = mutableListOf<String>()
        for (token in tokens) {
            if (token == "|") {
                result.add(last)
                last = mutableListOf()
            } else {
                last.add(token)
            }
        }
        result.add(last)
        return result
    }

    internal fun splitIntoTokens(input: String): List<String> {
        val tokens = mutableListOf<String>()
        var inDoubleQuotes = false
        var inSingleQuotes = false
        var currentString = ""
        for (i in 0 until input.length) {
            when (input[i]) {
                '"' -> {
                    if (inDoubleQuotes) {
                        inDoubleQuotes = false
                        tokens.add(substitution(currentString.trim()))
                        currentString = ""
                    } else if (!inSingleQuotes) {
                        inDoubleQuotes = true
                    } else {
                        currentString += input[i]
                    }
                }
                '\'' -> {
                    if (inSingleQuotes) {
                        inSingleQuotes = false
                        tokens.add(currentString.trim())
                        currentString = ""
                    } else if (!inDoubleQuotes) {
                        inSingleQuotes = true
                    } else {
                        currentString += input[i]
                    }
                }
                '|' -> {
                    if (inDoubleQuotes || inSingleQuotes) currentString += input[i]
                    else {
                        tokens.add(substitution(currentString.trim()))
                        currentString = ""
                        tokens.add("|")
                    }
                }
                ' ', '\t', '\n', '\r' -> {
                    if (inDoubleQuotes || inSingleQuotes) {
                        currentString += input[i]
                    } else {
                        tokens.add(substitution(currentString.trim()))
                        currentString = ""
                    }
                }
                else -> {
                    currentString += input[i]
                }
            }
        }
        tokens.add(substitution(currentString.trim()))
        return tokens.filter { s -> s.isNotEmpty() }
    }

    internal fun substitution(input: String): String {
        var result = ""
        var currentName = ""
        var inSubstitution = false
        for (i in 0 until input.length) {
            if (inSubstitution) {
                if (!(input[i] in 'a'..'z' || input[i] in 'A'..'Z' || input[i] in '0'..'9')) {
                    inSubstitution = false
                    result += env.getValue(currentName)
                    result += input[i]
                } else {
                    currentName += input[i]
                }
            } else if (input[i] == '$') {
                inSubstitution = true
                currentName = ""
            } else {
                result += input[i]
            }
        }
        if (inSubstitution) {
            result += env.getValue(currentName)
        }
        return result
    }
}