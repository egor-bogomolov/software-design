package ru.spbau.bogomolov.ast.commands

import ru.spbau.bogomolov.ast.AstNode
import java.io.File

/**
 * If first word in string is 'cat' then parsing is successful.
 */
fun parseCatFromString(string: String): Cat? {
    val words = string.toWords()
    if (words.isEmpty() || words[0] != "cat") {
        return null
    }
    return Cat(words.subList(1, words.size).toTextNodes())
}

/**
 * echo command. Arguments are treated as names of files and their content is printed. If input is provided then
 * arguments are ignored and input is printed.
 */
class Cat(args: List<AstNode>) : CommandWithArguments(args, "cat") {
    override fun shouldExit() = false

    override fun consumeArgument(arg: String) {
        appendToOutput(File(arg).inputStream().bufferedReader().use { it.readText() } + "\n")
    }

    override fun consumeInput(input: String) {
        appendToOutput(input + "\n")
    }
}