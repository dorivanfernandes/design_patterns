package com.dorivan.patterns.creational.singleton

/*
guarantee that class have just one instance and this instance exists globally
 */

// The Object is the recommended structure to create a singleton in Kotlin, but I make as class to learn the concept
class Logger private constructor() {
    val logs = mutableListOf<String>()
    companion object{
        private var instance: Logger? = null
        fun getInstance(): Logger {
            if (instance == null) {
                instance = Logger()
            }
            return instance!!
        }

    }

    fun log(message: String) = logs.add(message)

    fun showLogs() = logs.forEach(::println)
}

fun main() {
    val logger: Logger = Logger.getInstance()
    logger.log("test 1")
    logger.log("test 2")

    // create other variable to show that is the same instance. The test 3 was logged in the "first instance" logger
    val logger2: Logger = Logger.getInstance()
    logger2.log("test 3")

    logger.showLogs()
}