package com.github.xingray.kotlinbase.interfaces.function.index

fun interface LongIndexFunction<T> {
    fun apply(t: T, index: Int): Long
}
