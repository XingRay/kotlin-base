package com.github.xingray.kotlinbase.interfaces.function

fun interface BooleanFunction<T> {
    fun apply(t: T): Boolean
}
