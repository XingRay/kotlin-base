package com.github.xingray.kotlinbase.log.logger

class ConsoleLogger : Logger {
    override fun log(message: String) {
        println(message)
    }
}