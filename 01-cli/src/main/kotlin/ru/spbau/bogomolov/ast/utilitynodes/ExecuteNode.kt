package ru.spbau.bogomolov.ast.utilitynodes

import ru.spbau.bogomolov.ast.AstNode

/**
 * Content is treated as bash command that should be executed via normal bash.
 */
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