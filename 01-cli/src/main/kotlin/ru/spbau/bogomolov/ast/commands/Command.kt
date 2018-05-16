package ru.spbau.bogomolov.ast.commands

import ru.spbau.bogomolov.ast.AstNode

/**
 * The most general version of command. It takes list of nodes as arguments, invokes them and consumes their output.
 * To use it you should implement consumeArgument.
 */
abstract class Command(
        private val args: List<AstNode>,
        private val name: String,
        private val shouldHaveArguments: Boolean,
        private val isArg: Boolean
) : AstNode {

    private var output: String = ""
    private var errors: String = ""

    /**
     * Clears output and error output.
     * Should be overridden if something else should be cleared between invocations.
     */
    protected open fun reset() {
        output = ""
        errors = ""
    }

    /**
     * Logic that should be run for each argument. Arguments are consumed in left-to-right order. Argument is
     * guaranteed to be invoked before consuming.
     */
    abstract fun consumeArgument(arg: AstNode)

    /**
     * Calls consumeArgument for output of each child-node in left-to-right order. Nodes are invoked beforehand.
     */
    private fun collectArguments(args: List<AstNode>, consume: (AstNode) -> Unit) {
        try {
            args.forEach { node ->
                node.invoke()
                if (node.getErrors() == "") {
                    consume(node)
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
        if (shouldHaveArguments && args.isEmpty()) {
            errors += "No arguments provided to $name\n"
        } else {
            collectArguments(args, consume = { consumeArgument(it) })
        }
    }

    override fun getOutput(): String = output

    override fun getErrors(): String = errors

    protected fun appendToOutput(string: String) {
        output += string
    }

    protected fun setOutput(string: String) {
        output = string
    }

    protected fun appendToErrors(string: String) {
        errors += string
    }

    override fun isArgument() = isArg
}