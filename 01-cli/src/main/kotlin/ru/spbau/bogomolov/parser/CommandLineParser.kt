package ru.spbau.bogomolov.parser

import ru.spbau.bogomolov.ast.AstNode

/**
 * Processes a string and returns a list of roots of ASTs corresponding to it.
 */
interface CommandLineParser {
    fun parse(input: String): List<AstNode>
}