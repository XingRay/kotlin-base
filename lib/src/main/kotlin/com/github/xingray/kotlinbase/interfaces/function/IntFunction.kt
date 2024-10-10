package com.github.xingray.kotlinbase.interfaces.function

fun interface IntFunction<T> {
    fun apply(t: T): Int
}
