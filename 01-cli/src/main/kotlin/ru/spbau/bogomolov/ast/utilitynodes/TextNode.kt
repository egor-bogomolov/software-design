package ru.spbau.bogomolov.ast.utilitynodes

import ru.spbau.bogomolov.ast.AstNode

/**
 * AST node holding text. Saved text is returned when node is invoked without input, otherwise input is returned.
 */
class TextNode(private val text: String) : AstNode {

    private var output = text

    override fun shouldExit() = false

    override fun invoke() {
        output = text
    }

    override fun invoke(input: String) {
        output = input
    }

    override fun getOutput(): String = output

    override fun getErrors(): String = ""

}