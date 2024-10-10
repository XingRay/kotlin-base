package com.github.xingray.kotlinbase.task

interface Task {
    fun calc(): Int
    fun exec(progressCallback: ((progress: Int, total: Int) -> Unit)?): Result
}