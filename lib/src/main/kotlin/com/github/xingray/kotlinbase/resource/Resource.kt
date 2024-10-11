package com.github.xingray.kotlinbase.resource

import com.github.xingray.kotlinbase.ext.io.ensureFileExists
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

/**
 * 远程资源或者本地资源, 文件等最终可以读取成二进制数据
 */
interface Resource {
    fun <R> use(consumer: (InputStream) -> R): R?

    fun copyTo(file: File) {
        file.ensureFileExists()
        use { inputStream ->
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }

    fun copyTo(path: Path) {
        path.ensureFileExists()
        use { inputStream ->
            Files.newOutputStream(path).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }
}