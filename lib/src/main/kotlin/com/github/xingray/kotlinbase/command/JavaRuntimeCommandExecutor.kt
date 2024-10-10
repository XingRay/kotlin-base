package com.github.xingray.kotlinbase.command

import com.github.xingray.kotlinbase.command.listener.ExecuteListener
import com.github.xingray.kotlinbase.util.SystemUtil
import java.io.*
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.concurrent.Executor
import java.util.function.Consumer

class JavaRuntimeCommandExecutor(charset: Charset? = StandardCharsets.UTF_8) : CommandExecutor {
    private val charset: Charset

    private var outReadExecutor: Executor? = null

    private var errorReadExecutor: Executor? = null

    private var env: MutableMap<String, String>? = null

    init {
        requireNotNull(charset) { "charset can not be null !" }
        this.charset = charset
    }

    fun setOutReadExecutor(executor: Executor?) {
        this.outReadExecutor = executor
    }

    fun setErrorReadExecutor(executor: Executor?) {
        this.errorReadExecutor = executor
    }

    fun putEnv(key: String, value: String) {
        if (env == null) {
            env = HashMap()
        }
        env!![key] = value
    }

    fun setReadExecutor(executor: Executor?) {
        setOutReadExecutor(executor)
        setErrorReadExecutor(executor)
    }


    override fun execute(cmd: Array<String>, environmentParams: Array<String>?, dir: File?, listener: ExecuteListener?): Int {
        cmd[0] = replaceCmdExecutor(cmd[0])

        try {
            val process = Runtime.getRuntime().exec(cmd, environmentParams, dir)

            val readOutTask = Runnable {
                try {
                    BufferedReader(InputStreamReader(process.inputStream, charset)).use { reader ->
                        var line: String
                        while ((reader.readLine().also { line = it }) != null) {
                            listener?.out(line)
                        }
                    }
                } catch (ex: IOException) {
                    ex.printStackTrace()
                    listener?.error(ex.message ?: ex.toString())
                }
            }
            if (this.outReadExecutor == null) {
                Thread(readOutTask).start()
            } else {
                outReadExecutor!!.execute(readOutTask)
            }

            val readErrorTask = Runnable {
                try {
                    BufferedReader(InputStreamReader(process.errorStream, charset)).use { reader ->
                        var line: String
                        while ((reader.readLine().also { line = it }) != null) {
                            listener?.error(line)
                        }
                    }
                } catch (ex: IOException) {
                    ex.printStackTrace()
                    listener?.error(ex.message ?: ex.toString())
                }
            }

            if (errorReadExecutor == null) {
                Thread(readErrorTask).start()
            } else {
                errorReadExecutor?.execute(readErrorTask)
            }

            process.waitFor()
            val exitValue = process.exitValue()
            process.destroy()
            listener?.onFinish(exitValue)
            return exitValue
        } catch (e: Exception) {
            e.printStackTrace()
            listener?.onError(e)
            return -1
        }
    }

    private fun replaceCmdExecutor(cmdExecutor: String): String {
        val executorFile = File(cmdExecutor)
        if (executorFile.exists()) {
            return cmdExecutor
        }

        val executionFile = findExecutionFile(cmdExecutor)
        if (executionFile != null) {
            val executionFileAbsolutePath = executionFile.absolutePath
            return executionFileAbsolutePath
        }

        return cmdExecutor
    }

    private fun findExecutionFile(cmdExecutor: String): File? {
        if (SystemUtil.isRunOnWindows()) {
            return findExecutionFileOnWindows(cmdExecutor)
        }
        // TODO: 2023/3/11 for linux mac
        return null
    }

    private fun findExecutionFileOnWindows(cmdExecution: String): File? {
        val env = System.getenv()
        if (env == null || env.isEmpty()) {
            return null
        }

        var pathKey: String? = null
        for (key in env.keys) {
            if (key.equals("path", ignoreCase = true)) {
                pathKey = key
            }
        }
        if (pathKey == null) {
            return null
        }
        val path = env[pathKey]
        if (path.isNullOrEmpty()) {
            return null
        }
        val locations = path.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val index = cmdExecution.lastIndexOf(".")
        if (index >= 0) {
            val cmdSuffix = cmdExecution.substring(index)
            println(cmdSuffix)
            for (location in locations) {
                val file = File(location, cmdExecution)
                if (file.exists()) {
                    return file
                }
            }
        } else {
            val suffixes = arrayOf(".exe", ".cmd", ".bat", ".com", "")
            for (location in locations) {
                for (suffix in suffixes) {
                    val file = File(location, cmdExecution + suffix)
                    if (file.exists()) {
                        return file
                    }
                }
            }
        }
        return null
    }

    fun read(inputStream: InputStream, charset: Charset?, consumer: Consumer<String?>?) {
        val inInputStreamReader = if (charset != null) {
            InputStreamReader(inputStream, charset)
        } else {
            InputStreamReader(inputStream)
        }

        val inputBufferedReader = BufferedReader(inInputStreamReader)
        inputBufferedReader.lines().forEach(consumer)
        inInputStreamReader.close()
        inputStream.close()
    }
}
