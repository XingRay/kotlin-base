package com.github.xingray.kotlinbase.container.interfaces.series

interface Series<T> {
    fun get(index: Int): T

    fun length(): Int
}