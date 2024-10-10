package com.github.xingray.kotlinbase.ext


fun Thread.info(): String {
    val thread = this
    val name = thread.name.take(10).padEnd(10)
    val id = thread.threadId().toString().take(10).padEnd(10)
    val threadInfo = ("@${name}#${id}")
    return threadInfo
}