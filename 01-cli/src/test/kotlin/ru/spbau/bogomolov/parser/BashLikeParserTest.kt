package ru.spbau.bogomolov.parser

import org.mockito.Mockito.`when`
import ru.spbau.bogomolov.environment.LocalMapEnvironment
import kotlin.test.Test
import kotlin.test.assertEquals
import org.mockito.Mockito.mock
import ru.spbau.bogomolov.environment.Environment

class BashLikeParserTest {

    companion object {
        private const val key1 = "key1234567890"
        private const val val1 = "val1"
        private const val key2 = "key2"
        private const val val2 = "val2"
        private const val key3 = "key3"
        private const val valEmpty = ""

        private val mockedEnv = mock(Environment::class.java)

        init {
            `when`(mockedEnv.getValue(key1)).thenReturn(val1)
            `when`(mockedEnv.getValue(key2)).thenReturn(val2)
            `when`(mockedEnv.getValue(key3)).thenReturn(valEmpty)
        }
    }

    @Test
    fun splitIntoTokens() {
        val parser = BashLikeParser(mockedEnv)
        val cases: List<CaseStringSplit> = listOf(
                CaseStringSplit("abacaba", listOf("abacaba")),
                CaseStringSplit("aba caba", listOf("aba", "caba")),
                CaseStringSplit("   ab    a   caba", listOf("ab", "a", "caba")),
                CaseStringSplit("\"abacaba\"", listOf("abacaba")),
                CaseStringSplit("\"\"abacaba\"\"", listOf("abacaba")),
                CaseStringSplit("\"\'abacaba\'\"", listOf("\'abacaba\'")),
                CaseStringSplit("\"abacaba\"", listOf("abacaba")),
                CaseStringSplit("\"a b     a c a b a\"", listOf("a b     a c a b a")),
                CaseStringSplit("\"a b     a c a b a\" \"a b     a c a b a\"",
                        listOf("a b     a c a b a", "a b     a c a b a")),
                CaseStringSplit("aba|caba|aba", listOf("aba", "|", "caba", "|", "aba")),
                CaseStringSplit("\"aba|caba|aba\"", listOf("aba|caba|aba")),
                CaseStringSplit("\'aba|caba|aba\'", listOf("aba|caba|aba")),
                CaseStringSplit("aba | caba | aba", listOf("aba", "|", "caba", "|", "aba")),
                CaseStringSplit("\"aba | caba | aba\"", listOf("aba | caba | aba")),
                CaseStringSplit("\$$key1", listOf(val1)),
                CaseStringSplit("\'\$$key1\'", listOf("\$$key1")),
                CaseStringSplit("\"\$$key1\"", listOf(val1))
        )

        for (case in cases) {
            assertEquals(case.tokens, parser.splitIntoTokens(case.input))
        }
    }

    data class CaseStringSplit(val input: String, val tokens: List<String>)

    @Test
    fun splitTokensByPipes() {
        val parser = BashLikeParser(mockedEnv)
        val cases: List<CaseTokenSplit> = listOf(
                CaseTokenSplit(listOf("a", "a", "a"), listOf(
                        listOf("a", "a", "a")
                )),
                CaseTokenSplit(listOf("a", "|", "a"), listOf(
                        listOf("a"),
                        listOf("a")
                )),
                CaseTokenSplit(listOf("a", "|", "a", "a"), listOf(
                        listOf("a"),
                        listOf("a", "a")
                )),
                CaseTokenSplit(listOf("|", "a", "a"), listOf(
                        emptyList(),
                        listOf("a", "a")
                )),
                CaseTokenSplit(listOf("a", "a", "|"), listOf(
                        listOf("a", "a"),
                        emptyList()
                )),
                CaseTokenSplit(listOf("a", "|", "|", "a"), listOf(
                        listOf("a"),
                        emptyList(),
                        listOf("a")
                ))
        )

        for (case in cases) {
            assertEquals(case.result, parser.splitTokensByPipes(case.tokens))
        }
    }

    data class CaseTokenSplit(val tokens: List<String>, val result: List<List<String>>)

    @Test
    fun substitution() {
        val parser = BashLikeParser(mockedEnv)
        val cases: List<CaseSubstitution> = listOf(
                CaseSubstitution("\$$key1", val1),
                CaseSubstitution("\$$key1", val1),
                CaseSubstitution("\$$key2", val2),
                CaseSubstitution("\$$key3", valEmpty),
                CaseSubstitution("aaaa\$${key1} aaaa", "aaaa${val1} aaaa")
        )

        for (case in cases) {
            assertEquals(case.result, parser.substitution(case.input))
        }
    }

    data class CaseSubstitution(val input: String, val result: String)

}
