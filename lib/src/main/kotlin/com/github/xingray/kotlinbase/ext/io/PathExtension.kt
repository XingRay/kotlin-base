package com.github.xingray.kotlinbase.ext.io

import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.*

fun Path.exist(): Boolean {
    return Files.exists(this)
}

fun Path.ensureDirExists(): Path {
    val path = this
    if (Files.exists(path)) {
        if (path.isRegularFile()) {
            path.deleteIfExists()
            path.createDirectories()
        }
    } else {
        path.createDirectories()
    }

    return path
}

@OptIn(ExperimentalPathApi::class)
fun Path.ensureFileExists(): Path {
    val path = this
    if (Files.exists(path)) {
        if (path.isDirectory()) {
            path.deleteRecursively()
            path.createFile()
        }
    } else {
        val parent = path.toAbsolutePath().parent
        if (!parent.exist()) {
            parent.createDirectories()
        }
        path.createFile()
    }

    return path
}

fun Path.delete() {
    Files.delete(this)
}

fun Path.outputStream(): OutputStream {
    return Files.newOutputStream(this)
}