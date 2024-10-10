package com.github.xingray.kotlinbase.ext.array

import java.nio.ByteBuffer


fun ByteArray.toDoubleArray(): DoubleArray {
    val byteArray = this
    val byteBuffer = ByteBuffer.wrap(byteArray)
    val doubleArray = DoubleArray(byteArray.size / 8)
    for (i in doubleArray.indices) {
        doubleArray[i] = byteBuffer.getDouble()
    }
    return doubleArray
}