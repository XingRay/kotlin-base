package com.github.xingray.kotlinbase.log

import com.github.xingray.kotlinbase.ext.info
import com.github.xingray.kotlinbase.ext.io.ensureDirExists
import com.github.xingray.kotlinbase.ext.io.ensureFileExists
import com.github.xingray.kotlinbase.ext.io.isExistDir
import com.github.xingray.kotlinbase.log.logger.ConsoleLogger
import com.github.xingray.kotlinbase.log.logger.FileLogger
import com.github.xingray.kotlinbase.log.logger.Logger
import com.github.xingray.kotlinbase.util.TimeUtil
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Log {
    private const val TAG_MAX_LEN = 20
    private const val THREAD_INFO_MAX_LEN = 10

    private val mLoggers = mutableListOf<Logger>(
        ConsoleLogger()
    )

    fun setLogDir(logDir: File) {
        deleteOldLogFiles(logDir)
        logDir.ensureDirExists()
        val logFile = logDir.resolve("${TimeUtil.nowToString("yyyyMMddhhmmss")}.log").ensureFileExists()
        val fileLogger = FileLogger(logFile)
        mLoggers.add(fileLogger)
    }

    fun addLogger(logger: Logger) {
        mLoggers.add(logger)
    }

    fun deleteOldLogFiles(logDir: File) {
        if (!logDir.isExistDir()) {
            return
        }
        val logFiles = logDir.listFiles { _, name -> name.endsWith(".log") }

        val formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss")
        val tenDaysAgo = LocalDate.now().minusDays(10)

        logFiles?.forEach { file ->
            val fileName = file.nameWithoutExtension
            val fileDate = LocalDate.parse(fileName, formatter)

            if (fileDate.isBefore(tenDaysAgo)) {
                file.delete()
                d("Log", "Deleted log file: ${file.name}")
            }
        }
    }

    @JvmStatic
    fun d(tag: String?, s: String) {
        val formattedTag = tag?.take(TAG_MAX_LEN)?.padEnd(TAG_MAX_LEN) ?: " ".repeat(TAG_MAX_LEN)
        val message = "${Thread.currentThread().info()}\t${formattedTag}\t${s}"
        mLoggers.forEach { it.log(message) }
    }

    @JvmStatic
    fun e(tag: String?, s: String) {
        val formattedTag = tag?.take(TAG_MAX_LEN)?.padEnd(TAG_MAX_LEN) ?: " ".repeat(TAG_MAX_LEN)
        val message = "${Thread.currentThread().info()}\t${formattedTag}\t${s}"
        mLoggers.forEach { it.log(message) }
    }

    @JvmStatic
    fun w(tag: String?, s: String) {
        val formattedTag = tag?.take(TAG_MAX_LEN)?.padEnd(TAG_MAX_LEN) ?: " ".repeat(TAG_MAX_LEN)
        val message = "${Thread.currentThread().info()}\t${formattedTag}\t${s}"
        mLoggers.forEach { it.log(message) }
    }
}