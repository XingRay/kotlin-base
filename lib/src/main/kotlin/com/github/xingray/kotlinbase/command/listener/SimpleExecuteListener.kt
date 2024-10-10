package com.github.xingray.kotlinbase.command.listener

abstract class SimpleExecuteListener : ExecuteListener {
    override fun out(line: String) {

    }

    override fun error(line: String) {

    }

    override fun onFinish(exitValue: Int) {
    }

    override fun onError(e: Exception) {

    }
}