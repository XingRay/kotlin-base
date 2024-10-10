package com.github.xingray.kotlinbase.interfaces.function.index

fun interface ByteIndexFunction<T> {
    fun apply(t: T, index: Int): Byte
}
