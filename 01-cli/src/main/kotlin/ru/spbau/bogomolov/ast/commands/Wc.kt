package ru.spbau.bogomolov.ast.commands

import ru.spbau.bogomolov.ast.AstNode
import java.io.File

/**
 * If first token is 'wc' then parsing is successful, other tokens are treated as arguments.
 * If inputNodes are provided then they replace arguments.
 */
fun parseWcFromTokens(tokens: List<String>, inputNodes: List<AstNode>?): Wc? {
    if (tokens.isEmpty() || tokens[0] != "wc") {
        return null
    }
    inputNodes?.let { Wc(inputNodes) }
    return Wc(tokens.subList(1, tokens.size).toTextNodes())
}

/**
 * wc command. Arguments are treated as names of files. For each, size in bytes, words and lines is computed.
 * If input-arg is provided then its treated as text for which size is computed.
 */
class Wc(args: List<AstNode>) : Command(args, "wc", true, false) {

    override fun consumeArgument(arg: AstNode) {
        if (arg.isArgument()) {
            consumeFilename(arg.getOutput())
        } else {
            consumeInput(arg.getOutput())
        }
    }

    override fun shouldExit() = false

    private var bytes = 0
    private var words = 0
    private var lines = 0

    override fun reset() {
        super.reset()
        bytes = 0
        words = 0
        lines = 0
    }

    private fun consumeFilename(filename: String) {
        val file = File(filename)
        if (file.isDirectory) {
            throw IllegalArgumentException("$filename is a directory")
        }
        val linesList = file.readLines()
        val lines = linesList.size
        val bytes = file.readBytes().size
        val words = linesList.sumBy { it.toWords().size }
        appendToOutput("$lines $words $bytes $filename\n")
    }

    private fun consumeInput(input: String) {
        lines = input.count { it == '\n' } + if (input.last() == '\n') 0 else 1
        words += input.toWords().size
        bytes += input.length
        setOutput("$lines $words $bytes\n")
    }

}