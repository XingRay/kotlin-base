package com.github.xingray.kotlinbase.log.logger

import java.io.File
import java.io.FileWriter
import java.io.IOException


class FileLogger(private val logFile: File) : Logger {

    private val fileWriter = FileWriter(logFile, true)

    override fun log(message: String) {
        try {
            fileWriter.use { it.write("$message\n") }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}