package com.github.xingray.kotlinbase.interfaces.function.index

fun interface FloatIndexFunction<T> {
    fun apply(t: T, index: Int): Float
}
