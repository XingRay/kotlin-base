package com.nanosecond.makeup.kotlin.number

import java.nio.ByteBuffer

fun DoubleArray.toByteArray(): ByteArray {
    val data = this
    val byteBuffer = ByteBuffer.allocate(data.size * 8)
    for (value in data) {
        byteBuffer.putDouble(value)
    }
    return byteBuffer.array()
}

fun DoubleArray?.nullOrEmpty(): Boolean {
    return this == null || this.isEmpty()
}