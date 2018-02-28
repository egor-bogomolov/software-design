package ru.spbau.bogomolov.environment

class LocalMapEnvironment : Environment {

    private val map = mutableMapOf<String, String>()

    override fun getValue(name: String): String = map.getOrDefault(name, "")

    override fun setValue(name: String, value: String) {
        map[name] = value
    }
}