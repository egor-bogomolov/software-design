package ru.spbau.bogomolov.ast

/**
 * Represents a node in abstract syntax tree of bash-like language.
 */
interface AstNode {

    fun shouldExit(): Boolean

    /**
     * Invokes node with no input. Input here means output of another command passed via pipe.
     */
    fun invoke()

    /**
     * Invokes node with input. Most of the time it means that node's arguments will be ignored.
     */
    fun invoke(input: String)

    fun getOutput(): String

    fun getErrors(): String
}