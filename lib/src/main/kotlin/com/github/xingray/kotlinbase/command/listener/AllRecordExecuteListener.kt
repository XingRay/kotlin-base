package com.github.xingray.kotlinbase.command.listener

class AllRecordExecuteListener(private val separator: String? = null) : RecordExecuteListener {
    private val builder = StringBuilder()

    override fun out(line: String) {
        builder.append(line)
        if (separator != null) {
            builder.append(separator)
        }
    }

    override fun error(line: String) {
        builder.append(line)
        if (separator != null) {
            builder.append(separator)
        }
    }

    override fun onFinish(exitValue: Int) {
    }

    override fun onError(e: Exception) {
    }

    override fun getRecord(): String {
        return builder.toString()
    }
}
