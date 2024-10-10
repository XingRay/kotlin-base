package com.github.xingray.kotlinbase.interfaces.function.index

fun interface IntIndexFunction<T> {
    fun apply(t: T, index: Int): Int
}
