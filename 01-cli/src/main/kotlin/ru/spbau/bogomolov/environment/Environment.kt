package ru.spbau.bogomolov.environment

interface Environment {
    fun setValue(name: String, value: String)
    fun getValue(name: String): String
}