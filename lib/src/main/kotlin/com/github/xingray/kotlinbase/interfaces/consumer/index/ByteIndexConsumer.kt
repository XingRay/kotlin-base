package com.github.xingray.kotlinbase.interfaces.consumer.index

fun interface ByteIndexConsumer {
    fun accept(value: Byte, index: Int)
}
