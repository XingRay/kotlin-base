package com.github.xingray.kotlinbase.os

import java.io.File
import java.io.InputStream

interface CmdExecutor {
    fun execCmd(cmd: String)

    fun execCmdAsByteArray(cmd: String): ByteArray

    fun execCmdAsFile(cmd: String, file: File)

    fun execCmdAsLines(cmd: String): List<String>

    fun execCmdAsString(cmd: String): String

    fun <R> execCmdAsInputStream(cmd: String, mapper: (InputStream) -> R): R
}