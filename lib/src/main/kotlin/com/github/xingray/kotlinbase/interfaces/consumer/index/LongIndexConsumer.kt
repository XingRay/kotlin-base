package com.github.xingray.kotlinbase.interfaces.consumer.index

fun interface LongIndexConsumer {
    fun accept(value: Long, index: Int)
}
