package com.github.xingray.kotlinbase.command.listener

interface RecordExecuteListener : ExecuteListener {
    fun getRecord(): String
}