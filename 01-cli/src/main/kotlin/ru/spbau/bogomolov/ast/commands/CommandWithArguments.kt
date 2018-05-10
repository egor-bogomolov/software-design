package ru.spbau.bogomolov.ast.commands

import ru.spbau.bogomolov.ast.AstNode

/**
 * Generalization of command that has arguments.
 * To use it consumeArgument and consumeInput should be overrided.
 */
abstract class CommandWithArguments(
        private val args: List<AstNode>,
        private val name: String
) : Command {

    private var output: String = ""
    private var errors: String = ""

    /**
     * Clears output and error output.
     */
    protected open fun reset() {
        output = ""
        errors = ""
    }

    /**
     * Logic that should be run for each argument. Arguments are consumed in left-to-right order.
     */
    abstract fun consumeArgument(arg: String)

    /**
     * Logic that should be run for input if it's presented.
     */
    abstract fun consumeInput(input: String)

    /**
     * Calls consumeArgument for output of each child-node in left-to-right order. Nodes are invoked beforehand.
     */
    protected fun collectArguments(args: List<AstNode>, consume: (String) -> Unit) {
        try {
            args.forEach { node ->
                node.invoke()
                if (node.getErrors() == "") {
                    consume(node.getOutput())
                } else {
                    errors += "Error in argument $name:\n" + node.getErrors()
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