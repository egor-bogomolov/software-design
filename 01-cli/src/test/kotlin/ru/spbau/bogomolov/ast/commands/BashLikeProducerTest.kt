package ru.spbau.bogomolov.ast.commands

import kotlin.test.Test
import org.mockito.Mockito
import ru.spbau.bogomolov.ast.AstNode
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame

class BashLikeProducerTest {
    companion object {
        private val mockedCommand = Mockito.mock(Command::class.java)
        private val parser1: (List<String>, List<AstNode>?) -> Command? = { tokens, inputNodes ->
            mockedCommand
        }
        private val parser2: (List<String>, List<AstNode>?) -> Command? = { tokens, inputNodes ->
            null
        }
    }

    @Test
    fun registerCommandParser() {
        val producer = BashLikeProducer()
        assertEquals(0, producer.registeredCommandParsers.size)
        producer.registerCommandParser(parser1)
        assertEquals(1, producer.registeredCommandParsers.size)
        producer.registerCommandParser(parser2)
        assertEquals(2, producer.registeredCommandParsers.size)
    }

    @Test
    fun parseFromTokens() {
        val producer = BashLikeProducer()
        assertNull(producer.parseFromTokens(emptyList(), emptyList()))
        producer.registerCommandParser(parser2)
        assertNull(producer.parseFromTokens(emptyList(), emptyList()))
        producer.registerCommandParser(parser1)
        assertSame(mockedCommand, producer.parseFromTokens(emptyList(), emptyList()))
    }
}