package ru.spbau.bogomolov.ast.utilitynodes

import ru.spbau.bogomolov.ast.AstNode
import java.io.InputStreamReader
import java.io.BufferedReader


/**
 * Content is treated as bash command that should be executed via normal bash.
 */
class ExecuteNode(private val text: String) : AstNode {

    private var output: String = ""
    private var errors: String = ""

    fun reset() {
        output = ""
        errors = ""
    }

    override fun shouldExit() = false

    override fun invoke() {
        this.invoke(text)
    }

    override fun invoke(input: String) {
        val outputBuffer = StringBuffer()

        val p: Process
        try {
            p = Runtime.getRuntime().exec(input)
            p.waitFor()
            val reader = BufferedReader(InputStreamReader(p.inputStream))

            var line: String?
            while (true) {
                line = reader.readLine()
                if (line == null) break
                outputBuffer.append(line + "\n")
            }

        } catch (e: Exception) {
            e.message?.let { errors += it }
        }

        output = outputBuffer.toString()
    }

    override fun getOutput() = output

    override fun getErrors() = errors
}