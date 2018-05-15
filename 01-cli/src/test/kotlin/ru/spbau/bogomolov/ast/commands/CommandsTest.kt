package ru.spbau.bogomolov.ast.commands

import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import ru.spbau.bogomolov.ast.AstNode
import kotlin.test.*

class CommandsTest {
    companion object {
        private const val commandName = "name"
        private const val errorText = "Unknown error\n"
        private const val outputText = "My output\n"
        private val mockedNodeWithErrors = mock(AstNode::class.java)
        private val mockedNodeWithOutput = mock(AstNode::class.java)

        init {
            `when`(mockedNodeWithErrors.getErrors()).thenReturn(errorText)
            `when`(mockedNodeWithOutput.getOutput()).thenReturn(outputText)
            `when`(mockedNodeWithOutput.getErrors()).thenReturn("")
        }
    }

    @Test
    fun generalCommandNoArguments() {
        val command = CommandWithNoArgs()
        command.invoke()
        assertEquals("", command.getOutput())
        assertEquals("No arguments provided to $commandName\n", command.getErrors())
    }

    class CommandWithNoArgs : Command(emptyList(), commandName, true, false) {
        override fun consumeArgument(arg: AstNode) {}
        override fun shouldExit() = false
    }

    @Test
    fun generalCommandCollectErrors() {
        val command = CommandWithErrorArgs()
        command.invoke()
        assertEquals("Error in argument $commandName:\n${errorText}" +
                "Error in argument $commandName:\n${errorText}", command.getErrors())
    }

    class CommandWithErrorArgs : Command(
            listOf(mockedNodeWithErrors, mockedNodeWithErrors),
            commandName,
            true,
            false
    ) {
        override fun consumeArgument(arg: AstNode) {}
        override fun shouldExit() = false
    }

    @Test
    fun generalCommandConsumeArgs() {
        val command = CommandWithConsume()
        command.invoke()
        assertEquals("", command.getErrors())
        assertEquals("${outputText}${outputText}", command.getOutput())
    }

    class CommandWithConsume : Command(
            listOf(mockedNodeWithOutput, mockedNodeWithOutput),
            commandName,
            true,
            false
    ) {
        override fun consumeArgument(arg: AstNode) {
            appendToOutput(arg.getOutput())
        }

        override fun shouldExit() = false
    }
}