package ru.spbau.bogomolov.ast.commands

import ru.spbau.bogomolov.ast.AstNode
import java.io.File

fun parseWcFromString(string: String): Wc? {
    val words = string.toWords()
    if (words.isEmpty() || words[0] != "wc") {
        return null
    }
    return Wc(words.subList(1, words.size).toTextNodes())
}

class Wc(args: List<AstNode>) : CommandWithArguments(args, "cat") {

    private var bytes = 0
    private var words = 0
    private var lines = 0

    override fun reset() {
        super.reset()
        bytes = 0
        words = 0
        lines = 0
    }

    override fun consumeArgument(arg: String) {
        val file = File(arg)
        if (file.isDirectory) {
            throw IllegalArgumentException("$arg is a directory")
        }
        val linesList = file.readLines()
        val lines = linesList.size
        val bytes = file.readBytes().size
        val words = linesList.sumBy { it.toWords().size }
        appendToOutput("$lines $words $bytes $arg\n")
    }

    override fun consumeInput(input: String) {
        lines = 1
        words += input.toWords().size
        bytes += input.length
        setOutput("$lines $words $bytes\n")
    }

    override fun invoke(input: String) {
        reset()
        collectArguments(listOf(input).toTextNodes(), consume = { consumeInput(it) })
    }
}