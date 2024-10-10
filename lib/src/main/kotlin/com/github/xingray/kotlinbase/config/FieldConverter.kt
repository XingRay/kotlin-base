package com.github.xingray.kotlinbase.config

interface FieldConverter<T, C> {
    fun getConfig(targetField: T): C

    fun restoreConfig(targetField: T, state: C)
}