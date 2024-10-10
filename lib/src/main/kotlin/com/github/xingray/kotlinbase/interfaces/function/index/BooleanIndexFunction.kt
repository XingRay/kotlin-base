package com.github.xingray.kotlinbase.interfaces.function.index

fun interface BooleanIndexFunction<T> {
    fun apply(t: T, index: Int): Boolean
}
