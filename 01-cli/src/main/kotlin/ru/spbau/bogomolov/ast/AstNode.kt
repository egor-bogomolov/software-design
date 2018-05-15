package ru.spbau.bogomolov.ast

/**
 * Represents a node in abstract syntax tree of bash-like language.
 */
interface AstNode {

    fun shouldExit(): Boolean

    /**
     * Invokes node. After invocation output and error-output are stored and available via getOutput and getErrors.
     */
    fun invoke()

    fun getOutput(): String

    fun getErrors(): String

    /**
     * true if node is a straight argument of command in call (
     * Example:
     * echo "123" -> "123" is a straight argument
     * echo "123" | wc -> echo isn't a straight argument for wc
     */
    fun isArgument(): Boolean
}