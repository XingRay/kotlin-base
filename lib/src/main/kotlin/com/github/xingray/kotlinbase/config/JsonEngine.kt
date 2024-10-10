package com.github.xingray.kotlinbase.config

interface JsonEngine {
    fun <T> fromJson(json: String?, cls: Class<T>?): T

    fun <T> toJson(t: T): String?
}