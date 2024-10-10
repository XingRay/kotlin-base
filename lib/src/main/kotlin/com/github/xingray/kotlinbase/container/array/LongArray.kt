package com.github.xingray.kotlinbase.container.array

import com.github.xingray.kotlinbase.container.interfaces.series.LongSeries
import java.util.function.Consumer

class LongArray : Iterable<Long?>, LongSeries {
    private val array: kotlin.LongArray?

    constructor(array: kotlin.LongArray?) {
        this.array = array
    }

    constructor(length: Int) {
        array = kotlin.LongArray(length)
    }

    override fun get(index: Int): Long {
        return array!![index]
    }

    override fun length(): Int {
        return array!!.size
    }

    fun set(index: Int, value: Long) {
        array!![index] = value
    }

    fun toList(): List<Long> {
        val length = length()
        if (length == 0) {
            return emptyList()
        }
        val list = ArrayList<Long>(length)
        for (value in array!!) {
            list.add(value)
        }
        return list
    }

    override fun iterator(): Iterator<Long> {
        return ArrayIterator(this)
    }

    fun forEach(action: Consumer<in Long>) {
        if (array == null || array.size == 0) {
            return
        }
        for (value in array) {
            action.accept(value)
        }
    }

    private class ArrayIterator(private val array: LongArray) : Iterator<Long> {
        private var index = 0

        override fun hasNext(): Boolean {
            return array.length() > 0 && index < array.length()
        }

        override fun next(): Long {
            val value = array.get(index)
            index++
            return value
        }
    }

    companion object {
        fun of(vararg values: Long): LongArray? {
            if (values == null) {
                return null
            }
            return LongArray(values)
        }
    }
}