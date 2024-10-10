package com.github.xingray.kotlinbase.command

import com.github.xingray.kotlinbase.command.listener.ExecuteListener
import java.io.File

interface CommandExecutor {
    fun execute(cmd: Array<String>, environmentParams: Array<String>? = null, dir: File? = null, listener: ExecuteListener? = null): Int

    fun executeString(cmd: String, environmentParams: Array<String>? = null, dir: File? = null, listener: ExecuteListener? = null): Int {
        return execute(CommandUtil.splitCmd(cmd).toTypedArray(), environmentParams, dir, listener)
    }

    fun executeCmd(command: Any?, environmentParams: Array<String>? = null, dir: File? = null, listener: ExecuteListener? = null): Int {
        return execute(CommandUtil.commandToStringArray(command!!), environmentParams, dir, listener)
    }
}
