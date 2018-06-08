package ru.spbau.bogomolov.executor

import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import ru.spbau.bogomolov.ast.AstNode
import ru.spbau.bogomolov.environment.LocalMapEnvironment
import ru.spbau.bogomolov.parser.CommandLineParser
import kotlin.test.Test
import kotlin.test.assertEquals

class BashLikeExecutorTest {

    companion object {
        private const val nCalls = 5
        private const val string1 = "str1"
        private const val string2 = "str2"
        private val result1 = ExecutionResult(false, "output1", "errors1")
        private val result2 = ExecutionResult(true, "output2", "errors2")
        private val resultNull = ExecutionResult(false, "", "")
        private val mockedNode1 = mock(AstNode::class.java)
        private val mockedNode2 = mock(AstNode::class.java)

        init {
            `when`(mockedNode1.getOutput()).thenReturn(result1.output)
            `when`(mockedNode1.getErrors()).thenReturn(result1.error)
            `when`(mockedNode1.shouldExit()).thenReturn(result1.shouldExit)
            `when`(mockedNode2.getOutput()).thenReturn(result2.output)
            `when`(mockedNode2.getErrors()).thenReturn(result2.error)
            `when`(mockedNode2.shouldExit()).thenReturn(result2.shouldExit)
        }
    }

    @Test
    fun testProcess() {
        val executor = BashLikeExecutor(LocalMapEnvironment())
        val mockedParser = mock(CommandLineParser::class.java)
        `when`(mockedParser.parse(string1)).thenReturn(mockedNode1)
        `when`(mockedParser.parse(string2)).thenReturn(mockedNode2)

        for (i in 1..nCalls) {
            assertEquals(result1, executor.processString(string1, mockedParser))
            verify(mockedNode1, times(i)).invoke()
        }

        for (i in 1..nCalls) {
            assertEquals(result2, executor.processString(string2, mockedParser))
            verify(mockedNode2, times(i)).invoke()
        }
    }

    @Test
    fun testWithNull() {
        val executor = BashLikeExecutor(LocalMapEnvironment())
        val mockedParser = mock(CommandLineParser::class.java)
        `when`(mockedParser.parse(string1)).thenReturn(null)
        assertEquals(resultNull, executor.processString(string1, mockedParser))
    }
}
