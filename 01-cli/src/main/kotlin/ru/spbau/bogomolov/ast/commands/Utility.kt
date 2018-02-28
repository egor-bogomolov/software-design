package ru.spbau.bogomolov.ast.commands

import ru.spbau.bogomolov.ast.utilitynodes.TextNode

fun List<String>.toTextNodes() = this.map { TextNode(it) }

fun String.toWords() = this.split(' ')