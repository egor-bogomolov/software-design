package ru.spbau.bogomolov.environment

import kotlin.test.Test
import kotlin.test.assertEquals

class LocalMapEnvironmentTest {

    private val key1 = "str1"
    private val key2 = "str2"
    private val val1 = "val1"
    private val val2 = "val2"
    private val emptyRes = ""

    @Test
    fun setGet() {
        val env = LocalMapEnvironment()
        env.setValue(key1, val1)
        assertEquals(val1, env.getValue(key1))
    }

    @Test
    fun doubleGet() {
        val env = LocalMapEnvironment()
        env.setValue(key1, val1)
        assertEquals(val1, env.getValue(key1))
        assertEquals(val1, env.getValue(key1))
    }

    @Test
    fun getFromEmpty() {
        val env = LocalMapEnvironment()
        assertEquals(emptyRes, env.getValue(key1))
        env.setValue(key1, val1)
    }

    @Test
    fun doubleSet() {
        val env = LocalMapEnvironment()
        env.setValue(key1, val1)
        env.setValue(key1, val1)
        assertEquals(val1, env.getValue(key1))
        assertEquals(emptyRes, env.getValue(key2))
    }

    @Test
    fun multipleOperations() {
        val env = LocalMapEnvironment()
        env.setValue(key1, val1)
        assertEquals(val1, env.getValue(key1))
        env.setValue(key1, val2)
        assertEquals(val2, env.getValue(key1))
        assertEquals(emptyRes, env.getValue(key2))
        env.setValue(key2, val1)
        assertEquals(val1, env.getValue(key2))
    }
}