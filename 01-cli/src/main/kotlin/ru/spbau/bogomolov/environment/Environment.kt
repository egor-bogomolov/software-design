package ru.spbau.bogomolov.environment

/**
 * Stores variables and their values. Both values and names are strings.
 */
interface Environment {
    fun setValue(name: String, value: String)
    fun getValue(name: String): String
}