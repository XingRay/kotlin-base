package com.github.xingray.kotlinbase.interfaces.function.index

fun interface DoubleIndexFunction<T> {
    fun apply(t: T, index: Int): Double
}
