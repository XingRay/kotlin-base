package android.util

import android.util.logger.ConsoleLogger
import android.util.logger.FileLogger
import android.util.logger.Logger
import com.github.xingray.kotlinbase.ext.info
import com.github.xingray.kotlinbase.ext.io.ensureDirExists
import com.github.xingray.kotlinbase.util.TimeUtil
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Log {
    private const val TAG_MAX_LEN = 20
    private const val THREAD_INFO_MAX_LEN = 10

    private var mLogDir: File = File("log")
    private var mLogFile = mLogDir.resolve("${TimeUtil.nowToString("yyyyMMddhhmmss")}.log")
    private var mFileLogger = FileLogger(mLogFile)

    private val mLoggers = mutableListOf(
        ConsoleLogger(),
        mFileLogger
    )

    init {
        // 在每次启动时自动删除10天前的日志文件
        deleteOldLogFiles()
    }

    fun setLogDir(dir: File) {
        mLogDir = dir.ensureDirExists()
        mLogFile = mLogDir.resolve("${TimeUtil.nowToString("yyyyMMddhhmmss")}.log")

        mLoggers.remove(mFileLogger)
        mFileLogger = FileLogger(mLogFile)
        mLoggers.add(mFileLogger)
    }

    fun addLogger(logger: Logger) {
        mLoggers.add(logger)
    }

    fun deleteOldLogFiles() {
        val logDir = mLogDir
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