package ru.spbau.bogomolov.ast.utilitynodes

import kotlin.test.*

class NodesTest {
    @Test
    fun executeNodeEcho() {
        val node = ExecuteNode(listOf("echo 123"))
        node.invoke()
        assertFalse(node.shouldExit())
        assertEquals("", node.getErrors())
        assertEquals("123\n", node.getOutput())
    }

    @Test
    fun executeNodeEchoTokens() {
        val node = ExecuteNode(listOf("echo", "123"))
        node.invoke()
        assertFalse(node.shouldExit())
        assertEquals("", node.getErrors())
        assertEquals("123\n", node.getOutput())
    }

    @Test
    fun textNodeArg() {
        assertTrue(TextNode("", true).isArgument())
        assertFalse(TextNode("", false).isArgument())
    }

    @Test
    fun textNodeText() {
        val string = "3asdKLJ#%!241234"
        assertEquals(string, TextNode(string, false).getOutput())
        assertEquals("", TextNode("", false).getOutput())
    }
}