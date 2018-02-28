package ru.spbau.bogomolov.ast.utilitynodes

import ru.spbau.bogomolov.ast.AstNode

class ExecuteNode(text: String) : AstNode {
    override fun shouldExit() = false

    override fun invoke() {

    }

    override fun invoke(input: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOutput(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getErrors(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}