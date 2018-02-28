package ru.spbau.bogomolov.ast

/**
 * Represents a node in abstract syntax tree of bash-like language.
 */
interface AstNode {
    fun invoke()
    fun getOutput(): String
    fun getErrors(): String
}