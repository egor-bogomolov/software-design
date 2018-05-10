package ru.spbau.bogomolov.environment

/**
 * Stores variables and their values. Can wrap a Map in local storage as well as a database.
 */
interface Environment {
    fun setValue(name: String, value: String)
    fun getValue(name: String): String
}