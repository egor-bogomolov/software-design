package ru.spbau.bogomolov.ast.commands

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import ru.spbau.bogomolov.ast.AstNode
import java.io.File

/**
 * If first token is 'grep' then parsing is successful, other tokens are treated as arguments.
 * If inputNodes are provided then they are appended to arguments list.
 */
fun parseGrepFromTokens(tokens: List<String>, inputNodes: List<AstNode>?): Grep? {
    if (tokens.isEmpty() || tokens[0] != "grep") {
        return null
    }
    val arguments =
            if (inputNodes == null) tokens.subList(1, tokens.size).toTextNodes(true)
            else tokens.subList(1, tokens.size).toTextNodes(true) + inputNodes
    return Grep(arguments)
}

/**
 * Finds occurrences of pattern (can be regex) in file or text.
 * If source is provided in arguments then it's treated as filename.
 * If source is passed via pipe then it's treated as plain text.
 */
class Grep(args: List<AstNode>) : Command(args, "grep", true, false) {
    companion object {
        internal const val separationLine = "--------------"
    }

    private var hasInput = false
    private var argStrings = mutableListOf<String>()

    override fun reset() {
        super.reset()
        hasInput = false
        argStrings.clear()
    }

    override fun consumeArgument(arg: AstNode) {
        hasInput = hasInput || !arg.isArgument()
        argStrings.add(arg.getOutput())
    }

    override fun invoke() {
        super.invoke()
        try {
            val parsedArgs = ArgParser(argStrings.toTypedArray()).parseInto(::GrepArgs)
            val lines =
                    if (hasInput) {
                        parsedArgs.source.lines()
                    } else {
                        val filename = parsedArgs.source
                        val file = File(filename)
                        if (file.isDirectory) {
                            throw IllegalArgumentException("$filename is a directory")
                        }
                        file.readLines()
                    }

            var lastOccurrence = parsedArgs.linesAfter + 1
            var hasBroken = false
            val regex =
                    if (parsedArgs.ignoreCase) {
                        Regex(parsedArgs.pattern.toLowerCase())
                    } else {
                        Regex(parsedArgs.pattern)
                    }

            for (line in lines) {
                val searchLine =
                        if (parsedArgs.ignoreCase) {
                            line.toLowerCase()
                        } else {
                            line
                        }
                var found = false
                val matches = regex.findAll(searchLine)
                if (parsedArgs.wholeWords) {
                    for (match in matches) {
                        val prevIndex = match.range.start - 1
                        val nextIndex = match.range.endInclusive + 1
                        if ((prevIndex < 0 || !searchLine.get(prevIndex).isLetterOrDigit())
                                && (nextIndex >= searchLine.length || !searchLine.get(nextIndex).isLetterOrDigit())) {
                            found = true
                            break
                        }
                    }
                } else if (matches.count() > 0) {
                    found = true
                }
                if (found) {
                    lastOccurrence = 0
                }
                if (lastOccurrence <= parsedArgs.linesAfter) {
                    if (hasBroken && getOutput().isNotEmpty()) {
                        appendToOutput(separationLine + "\n")
                    }
                    appendToOutput(line + "\n")
                }
                hasBroken = lastOccurrence > parsedArgs.linesAfter
                lastOccurrence += 1
            }
        } catch (e: Exception) {
            e.message?.let { appendToErrors(it) }
        }
    }

    override fun shouldExit() = false

    private class GrepArgs(parser: ArgParser) {
        val ignoreCase by parser.flagging("-i", help = "Make search case-insensitive")

        val wholeWords by parser.flagging("-w",
                help = "Only occurrences surrounded by non-letter and non-digit symbols are taken into account")

        val linesAfter by parser.storing("-A",
                help = "Number of lines printed after occurrence") { toInt() }.default(0)

        val pattern by parser.positional("Pattern used in search, can be regex")

        val source by parser.positional("File or text in which pattern should be found")
    }
}