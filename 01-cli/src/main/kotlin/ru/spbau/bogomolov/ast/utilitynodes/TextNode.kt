package ru.spbau.bogomolov.ast.utilitynodes

import ru.spbau.bogomolov.ast.AstNode

class TextNode(private val text: String) : AstNode {

    private var output = text

    override fun invoke() {
        output = text
    }

    override fun invoke(input: String) {
        output = input
    }

    override fun getOutput(): String = output

    override fun getErrors(): String = ""

}