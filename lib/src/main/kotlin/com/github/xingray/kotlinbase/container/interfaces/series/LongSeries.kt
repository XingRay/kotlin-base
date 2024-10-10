package com.github.xingray.kotlinbase.container.interfaces.series

interface LongSeries {
    fun get(index: Int): Long

    fun length(): Int
}