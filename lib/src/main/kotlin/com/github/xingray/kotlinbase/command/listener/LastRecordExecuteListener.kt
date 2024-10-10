package com.github.xingray.kotlinbase.command.listener

class LastRecordExecuteListener : RecordExecuteListener {

    private var lastLine: String = ""

    override fun out(line: String) {
        lastLine = line
    }

    override fun error(line: String) {
        lastLine = line
    }

    override fun onFinish(exitValue: Int) {
    }

    override fun onError(e: Exception) {

    }

    override fun getRecord(): String {
        return lastLine
    }
}