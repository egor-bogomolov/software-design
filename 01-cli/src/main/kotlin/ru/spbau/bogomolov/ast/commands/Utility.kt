package ru.spbau.bogomolov.ast.commands

import ru.spbau.bogomolov.ast.utilitynodes.TextNode

/**
 * Transforms list of strings into list of text nodes representing those strings.
 */
fun List<String>.toTextNodes() = this.map { TextNode(it) }

fun String.toWords() = this.split("\n", "\t", " ").filterNot { s -> s.isEmpty() }