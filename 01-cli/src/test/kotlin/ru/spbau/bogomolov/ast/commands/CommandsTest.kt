package ru.spbau.bogomolov.ast.commands

import org.mockito.Mockito.*
import ru.spbau.bogomolov.ast.AstNode
import ru.spbau.bogomolov.environment.Environment
import kotlin.test.*

class CommandsTest {
    companion object {
        private val root = System.getProperty("user.dir")

        private const val commandName = "name"
        private const val errorText = "Unknown error\n"
        private const val outputText = "My output\n"
        private const val wcOutput = "Mama America\nPapa Russia\n"
        private const val varName = "var"
        private const val varValue = "value"
        private val grepInputText =
                """
                    0 On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and
                    1 demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee
                    2 the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their
                    3 duty through weakness of will, which is the same as saying through shrinking from toil and pain.
                    4 These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice
                    5 is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is
                    6 to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty
                    7 or the obligations of business it will frequently occur that pleasures have to be repudiated and
                    8 annoyances accepted. The wise man therefore always holds in these matters to this principle of
                    9 selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to
                    10 avoid worse pains
                """.trimIndent()

        private val mockedNodeWithErrors = mock(AstNode::class.java)
        private val mockedNodeWithOutput = mock(AstNode::class.java)
        private val mockedWcInput = mock(AstNode::class.java)
        private val mockedWcFile = mock(AstNode::class.java)
        private val mockedEchoInput = mock(AstNode::class.java)
        private val mockedEchoArgs = mock(AstNode::class.java)
        private val mockedCatFile = mock(AstNode::class.java)
        private val mockedGrepInput = mock(AstNode::class.java)
        private val mockedGrepFile = mock(AstNode::class.java)
        private val mockedEnvironment = mock(Environment::class.java)

        init {
            `when`(mockedNodeWithErrors.getErrors()).thenReturn(errorText)

            `when`(mockedNodeWithOutput.getOutput()).thenReturn(outputText)
            `when`(mockedNodeWithOutput.getErrors()).thenReturn("")

            `when`(mockedWcInput.getOutput()).thenReturn(wcOutput)
            `when`(mockedWcInput.getErrors()).thenReturn("")
            `when`(mockedWcInput.isArgument()).thenReturn(false)

            `when`(mockedWcFile.getOutput()).thenReturn("""$root/src/test/resources/wc.txt""")
            `when`(mockedWcFile.getErrors()).thenReturn("")
            `when`(mockedWcFile.isArgument()).thenReturn(true)

            `when`(mockedEchoInput.getOutput()).thenReturn(outputText)
            `when`(mockedEchoInput.getErrors()).thenReturn("")
            `when`(mockedEchoInput.isArgument()).thenReturn(false)

            `when`(mockedEchoArgs.getOutput()).thenReturn(outputText)
            `when`(mockedEchoArgs.getErrors()).thenReturn("")
            `when`(mockedEchoArgs.isArgument()).thenReturn(true)

            `when`(mockedCatFile.getOutput()).thenReturn("""$root/src/test/resources/cat.txt""")
            `when`(mockedCatFile.getErrors()).thenReturn("")
            `when`(mockedCatFile.isArgument()).thenReturn(true)

            `when`(mockedGrepInput.getOutput()).thenReturn(grepInputText)
            `when`(mockedGrepInput.getErrors()).thenReturn("")
            `when`(mockedGrepInput.isArgument()).thenReturn(false)

            `when`(mockedGrepFile.getOutput()).thenReturn("""$root/src/test/resources/grep.txt""")
            `when`(mockedGrepFile.getErrors()).thenReturn("")
            `when`(mockedGrepFile.isArgument()).thenReturn(true)
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

    @Test
    fun wcTestInput() {
        val wc = Wc(listOf(mockedWcInput))
        wc.invoke()
        assertEquals("2 4 25\n", wc.getOutput())
    }

    @Test
    fun wcTestFile() {
        val wc = Wc(listOf(mockedWcFile))
        wc.invoke()
        assertTrue(wc.getOutput().startsWith("5 15 59 "))
    }

    @Test
    fun pwdTest() {
        val pwd = Pwd()
        pwd.invoke()
        assertEquals(root + "\n", pwd.getOutput())
    }

    @Test
    fun exitTest() {
        val exit = Exit()
        exit.invoke()
        assertTrue(exit.shouldExit())
    }

    @Test
    fun echoTestInput() {
        val echo = Echo(listOf(mockedEchoInput))
        echo.invoke()
        assertEquals(outputText + "\n", echo.getOutput())
    }

    @Test
    fun echoTestArgs() {
        val echo = Echo(listOf(mockedEchoArgs, mockedEchoArgs))
        echo.invoke()
        assertEquals(outputText + "\n" + outputText + "\n", echo.getOutput())
    }

    @Test
    fun catTestFile() {
        val cat = Cat(listOf(mockedCatFile))
        cat.invoke()
        assertEquals("some random text\n" +
                "on several lines\n" +
                "that i'm writing now\n", cat.getOutput())
    }

    @Test
    fun assignmentTest() {
        val assignment = Assignment(mockedEnvironment, varName, varValue)
        assignment.invoke()
        verify(mockedEnvironment).setValue(varName, varValue)
    }

    private fun getGrepLines(numbers: List<Int>): String {
        val lines = grepInputText.lines()
        var result = ""
        for (number in numbers) {
            if (number == -1) {
                result += Grep.separationLine + "\n"
            } else {
                result += lines.get(number) + "\n"
            }
        }
        return result
    }

    @Test
    fun grepBasicTest() {
        val grep = Grep(listOf("duty").toTextNodes(true) + mockedGrepInput)
        grep.invoke()
        assertEquals(getGrepLines(listOf(3, -1, 6)), grep.getOutput())
    }

    @Test
    fun grepMultipleInOneLine() {
        val grep = Grep(listOf("is").toTextNodes(true) + mockedGrepInput)
        grep.invoke()
        assertEquals(getGrepLines(listOf(0, -1, 3, 4, 5, -1, 8)), grep.getOutput())
    }

    @Test
    fun grepSpecificCase() {
        val grep1 = Grep(listOf("In").toTextNodes(true) + mockedGrepInput)
        grep1.invoke()
        assertEquals(getGrepLines(listOf(4)), grep1.getOutput())

        val grep2 = Grep(listOf("IN").toTextNodes(true) + mockedGrepInput)
        grep2.invoke()
        assertEquals(getGrepLines(emptyList()), grep2.getOutput())
    }

    @Test
    fun grepIgnoreCase() {
        val grep = Grep(listOf("-i", "but").toTextNodes(true) + mockedGrepInput)
        grep.invoke()
        assertEquals(getGrepLines(listOf(6)), grep.getOutput())
    }

    @Test
    fun grepWholeWord() {
        val grep = Grep(listOf("-w", "is").toTextNodes(true) + mockedGrepInput)
        grep.invoke()
        assertEquals(getGrepLines(listOf(3, -1, 5)), grep.getOutput())
    }

    @Test
    fun grepMultipleLines() {
        val grep = Grep(listOf("-A", "3", "moment").toTextNodes(true) + mockedGrepInput)
        grep.invoke()
        assertEquals(getGrepLines(listOf(1, 2, 3, 4)), grep.getOutput())
    }

    @Test
    fun grepMergeMultipleLines() {
        val grep = Grep(listOf("-A", "1", "pleasure").toTextNodes(true) + mockedGrepInput)
        grep.invoke()
        assertEquals(getGrepLines(listOf(1, 2, -1, 5, 6, 7, 8, 9, 10)), grep.getOutput())
    }

    @Test
    fun grepMultipleArguments() {
        val grep = Grep(listOf("-A", "3", "-i", "but").toTextNodes(true) + mockedGrepInput)
        grep.invoke()
        assertEquals(getGrepLines(listOf(6, 7, 8, 9)), grep.getOutput())
    }

    @Test
    fun grepFromFileTest() {
        val grep = Grep(listOf("-w", "is").toTextNodes(true) + mockedGrepFile)
        grep.invoke()
        assertEquals(getGrepLines(listOf(3, -1, 5)), grep.getOutput())
    }



}