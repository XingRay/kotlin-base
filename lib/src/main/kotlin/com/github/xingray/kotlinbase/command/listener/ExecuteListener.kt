package com.github.xingray.kotlinbase.command.listener

interface ExecuteListener {
    fun out(line: String)

    fun error(line: String)

    fun onFinish(exitValue: Int)

    fun onError(e: Exception)
}