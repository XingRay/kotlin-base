package com.github.xingray.kotlinbase.util

import kotlin.random.Random

object RandomUtil {
    fun randomSubValue(value: Long, fromRatio: Float, toRatio: Float): Long {
        if (fromRatio > toRatio) {
            throw IllegalArgumentException("fromRatio > toRatio, fromRatio:${fromRatio}, toRatio:${toRatio}")
        }

        return Random.nextLong((value * fromRatio).toLong(), (value * toRatio).toLong())
    }
}