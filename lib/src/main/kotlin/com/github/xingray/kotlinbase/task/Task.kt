package com.nanosecond.makeup.kotlin.task

interface Task {
    fun calc(): Int
    fun exec(progressCallback: ((progress: Int, total: Int) -> Unit)?): Result
}