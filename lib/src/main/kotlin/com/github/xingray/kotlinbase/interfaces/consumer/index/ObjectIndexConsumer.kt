package com.github.xingray.kotlinbase.interfaces.consumer.index

fun interface ObjectIndexConsumer<T> {
    fun accept(value: T, index: Int)
}
