package ru.spbau.bogomolov.ast.commands

import ru.spbau.bogomolov.ast.AstNode

abstract class CommandWithArguments(
        private val args: List<AstNode>,
        private val name: String
) : Command {

    private var output: String = ""
    private var errors: String = ""

    protected open fun reset() {
        output = ""
        errors = ""
    }

    abstract fun consumeArgument(arg: String)

    abstract fun consumeInput(input: String)

    protected fun collectArguments(args: List<AstNode>, consume: (String) -> Unit) {
        try {
            args.forEach { node ->
                node.invoke()
                if (node.getErrors() == "") {
                    consume(node.getOutput())
                } else {
                    errors += "Error in argument of cat:\n" + node.getErrors()
                }
            }
        } catch (e: Exception) {
            e.message?.let { errors += it }
        }
    }

    override fun invoke() {
        reset()
        if (args.isEmpty()) {
            errors += "No arguments provided to $name\n"
        } else {
            collectArguments(args, consume = { consumeArgument(it) })
        }
    }

    override fun invoke(input: String) {
        reset()
        collectArguments(input.toWords().toTextNodes(), consume = { consumeInput(it) })
    }

    override fun getOutput(): String = output

    override fun getErrors(): String = errors

    protected fun appendToOutput(string: String) {
        output += string
    }

    protected fun setOutput(string: String) {
        output = string
    }
}