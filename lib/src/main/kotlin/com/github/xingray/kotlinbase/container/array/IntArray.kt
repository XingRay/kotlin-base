package com.github.xingray.kotlinbase.container.array

import com.github.xingray.kotlinbase.container.interfaces.series.IntSeries
import java.util.function.Consumer

class IntArray : Iterable<Int?>, IntSeries {
    private val array: kotlin.IntArray?

    constructor(array: kotlin.IntArray?) {
        this.array = array
    }

    constructor(length: Int) {
        array = kotlin.IntArray(length)
    }

    override fun get(index: Int): Int {
        return array!![index]
    }

    override fun length(): Int {
        return array!!.size
    }

    fun set(index: Int, value: Int) {
        array!![index] = value
    }

    fun toList(): List<Int> {
        val length = length()
        if (length == 0) {
            return emptyList()
        }
        val list = ArrayList<Int>(length)
        for (value in array!!) {
            list.add(value)
        }
        return list
    }

    override fun iterator(): Iterator<Int> {
        return ArrayIterator(this)
    }

    fun forEach(action: Consumer<in Int>) {
        if (array == null || array.size == 0) {
            return
        }
        for (value in array) {
            action.accept(value)
        }
    }

    private class ArrayIterator(private val array: IntArray) : Iterator<Int> {
        private var index = 0

        override fun hasNext(): Boolean {
            return array.length() > 0 && index < array.length()
        }

        override fun next(): Int {
            val value = array.get(index)
            index++
            return value
        }
    }

    companion object {
        fun of(vararg values: Int): IntArray? {
            if (values == null) {
                return null
            }
            return IntArray(values)
        }
    }
}