package com.github.xingray.kotlinbase.log.logger

import com.github.xingray.kotlinbase.ext.io.ensureFileExists
import java.io.File
import java.io.FileWriter
import java.io.IOException


class FileLogger(private val logFile: File) : Logger {

    override fun log(message: String) {
        try {
            logFile.ensureFileExists()
            FileWriter(logFile, true).use { it.write("$message\n") }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}