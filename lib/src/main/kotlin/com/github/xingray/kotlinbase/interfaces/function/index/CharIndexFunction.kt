package com.github.xingray.kotlinbase.interfaces.function.index

fun interface CharIndexFunction<T> {
    fun apply(t: T, index: Int): Char
}
