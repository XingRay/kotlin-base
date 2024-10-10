package com.github.xingray.kotlinbase.os

import java.io.File
import java.io.InputStream

class WindowsCmdExecutor : CmdExecutor {

    companion object {
        @JvmStatic
        private val TAG = WindowsCmdExecutor::class.java.simpleName

        private const val LOG_ENABLE = false
    }

    override fun execCmd(cmd: String) {
        if (LOG_ENABLE) {
        }

        val process = Runtime.getRuntime().exec(arrayOf("cmd", "/c", cmd))
        val exitCode = process.waitFor()
        if (exitCode != 0) {
        }
    }

    override fun execCmdAsByteArray(cmd: String): ByteArray {
        if (LOG_ENABLE) {
        }
        val process = Runtime.getRuntime().exec(arrayOf("cmd", "/c", cmd))
        val inputStream = process.inputStream
        val buffer = inputStream.use { it.readAllBytes() }
        val exitCode = process.waitFor()
        if (exitCode != 0) {
        }
        return buffer
    }

    override fun execCmdAsFile(cmd: String, file: File) {
        if (LOG_ENABLE) {
        }
        val process = Runtime.getRuntime().exec(arrayOf("cmd", "/c", cmd))
        process.inputStream.use { inputStream ->
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        val exitCode = process.waitFor()
        if (exitCode != 0) {
        }
    }

    override fun execCmdAsLines(cmd: String): List<String> {
        if (LOG_ENABLE) {
        }
        val process = Runtime.getRuntime().exec(arrayOf("cmd", "/c", cmd))
        val lines = process.inputStream.use { it.reader().readLines() }
        val exitCode = process.waitFor()
        if (exitCode != 0) {
        }
        return lines
    }

    override fun execCmdAsString(cmd: String): String {
        if (LOG_ENABLE) {
        }
        val process = Runtime.getRuntime().exec(arrayOf("cmd", "/c", cmd))
        val text = process.inputStream.use { it.reader().readText() }
        val exitCode = process.waitFor()
        if (exitCode != 0) {
        }
        return text.trim()
    }

    override fun <R> execCmdAsInputStream(cmd: String, mapper: (InputStream) -> R): R {
        if (LOG_ENABLE) {
        }
        val process = Runtime.getRuntime().exec(arrayOf("cmd", "/c", cmd))
        val result = process.inputStream.use { mapper(it) }
        val exitCode = process.waitFor()
        if (exitCode != 0) {
        }
        return result
    }
}