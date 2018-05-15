package ru.spbau.bogomolov.ast.utilitynodes

import ru.spbau.bogomolov.ast.AstNode

/**
 * AST node holding text. Saved text is returned as output.
 */
class TextNode(private val text: String, private val isArg: Boolean) : AstNode {

    override fun isArgument() = isArg

    private var output = text

    override fun shouldExit() = false

    override fun invoke() {
        output = text
    }

    override fun getOutput(): String = output

    override fun getErrors(): String = ""

}