package com.github.xingray.kotlinbase.interfaces.function.index

fun interface ObjectIndexFunction<T, R> {
    fun apply(t: T, index: Int): R
}