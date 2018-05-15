package ru.spbau.bogomolov.ast.commands

import ru.spbau.bogomolov.ast.AstNode

/**
 * If first token is 'pwd' then parsing is successful.
 */
fun parsePwdFromTokens(tokens: List<String>): Pwd? {
    if (tokens.isEmpty() || tokens[0] != "pwd") {
        return null
    }
    return Pwd()
}

/**
 * pwd command. Doesn't take arguments. Prints path to current directory.
 */
class Pwd : Command(emptyList(), "pwd", false, false) {

    override fun consumeArgument(arg: AstNode) {}

    override fun shouldExit() = false

    override fun invoke() {
        setOutput(System.getProperty("user.dir") + "\n")
    }
}