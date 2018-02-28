package ru.spbau.bogomolov.ast.commands

import ru.spbau.bogomolov.ast.AstNode
import java.io.File

fun parseCatFromString(string: String): Cat? {
    val words = string.toWords()
    if (words.isEmpty() || words[0] != "cat") {
        return null
    }
    return Cat(words.subList(1, words.size).toTextNodes())
}

class Cat(args: List<AstNode>) : CommandWithArguments(args, "cat") {
    override fun consumeArgument(arg: String) {
        appendToOutput(File(arg).inputStream().bufferedReader().use { it.readText() } + "\n")
    }

    override fun consumeInput(input: String) {
        appendToOutput(input + "\n")
    }
}