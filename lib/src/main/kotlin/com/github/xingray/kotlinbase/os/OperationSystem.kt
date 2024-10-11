package com.github.xingray.kotlinbase.os

import java.io.File

sealed class OperationSystem {

    data object Windows : OperationSystem() {
        override fun openDir(dir: File) {
            Runtime.getRuntime().exec(arrayOf("explorer.exe", dir.absolutePath))
        }

        override fun getCmdExecutor(): CmdExecutor {
            return WindowsCmdExecutor()
        }
    }

    data object Macos : OperationSystem() {
        override fun openDir(dir: File) {
            TODO("Not yet implemented")
        }

        override fun getCmdExecutor(): CmdExecutor {
            TODO("Not yet implemented")
        }
    }

    data object Linux : OperationSystem() {
        override fun openDir(dir: File) {
            TODO("Not yet implemented")
        }

        override fun getCmdExecutor(): CmdExecutor {
            TODO("Not yet implemented")
        }
    }

    companion object {
        fun getOs(): OperationSystem {
            val osName = System.getProperty("os.name").lowercase()

            return when {
                osName.contains("win") -> Windows
                osName.contains("mac") -> Macos
                osName.contains("nix") || osName.contains("nux") || osName.contains("aix") -> Linux
                else -> throw IllegalStateException("unknown os")
            }
        }
    }

    abstract fun openDir(dir: File)

    fun OperationSystem.getUserHome(): File {
        return File(System.getProperty("user.home"))
    }

    abstract fun getCmdExecutor(): CmdExecutor
}