package ru.spbau.bogomolov.ast

/**
 * Represents a node in abstract syntax tree of bash-like language.
 */
interface AstNode {
    fun shouldExit(): Boolean
    fun invoke()
    fun invoke(input: String)
    fun getOutput(): String
    fun getErrors(): String
}