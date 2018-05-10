package ru.spbau.bogomolov.parser.preprocessor

import ru.spbau.bogomolov.environment.Environment

/**
 * Finds pipes and splits string at their positions. Then performs substitution of variables on each string.
 */
class BashLikePreprocessor(private val env: Environment) : Preprocessor {

    private fun splitPipes(input: String): List<String> {
        val result = mutableListOf<String>()
        var inDoubleQuotes = false
        var inSingleQuotes = false
        var currentString = ""
        for (i in 0 until input.length) {
            if (input[i] == '"') {
                if (inDoubleQuotes) inDoubleQuotes = false
                else if (!inSingleQuotes) inDoubleQuotes = true
            }
            if (input[i] == '\'') {
                if (inSingleQuotes) inSingleQuotes = false
                else if (!inDoubleQuotes) inSingleQuotes = true
            }
            if (input[i] == '|' && !inSingleQuotes && !inDoubleQuotes) {
                result.add(currentString.trim())
                currentString = ""
            } else {
                currentString += input[i]
            }
        }
        result.add(currentString.trim())
        return result
    }

    private fun substitution(input: String): String {
        var result = ""
        var inDoubleQuotes = false
        var inSingleQuotes = false
        var currentName = ""
        var inSubstitution = false
        for (i in 0 until input.length) {
            if (inSubstitution && !(input[i] in 'a'..'z' || input[i] in 'A'..'Z' || input[i] in '0'..'9')) {
                inSubstitution = false
                result += env.getValue(currentName)
            }
            if (input[i] == '"') {
                if (inDoubleQuotes) inDoubleQuotes = false
                else if (!inSingleQuotes) inDoubleQuotes = true
            }
            if (input[i] == '\'') {
                if (inSingleQuotes) inSingleQuotes = false
                else if (!inDoubleQuotes) inSingleQuotes = true
            }
            if (input[i] == '$' && !inSingleQuotes) {
                inSubstitution = true
                currentName = ""
            } else if (!inSubstitution) {
                result += input[i]
            }
        }
        if (inSubstitution) {
            result += env.getValue(currentName)
        }
        return result
    }

    override fun process(input: String) = splitPipes(input).map { substitution(it) }
}