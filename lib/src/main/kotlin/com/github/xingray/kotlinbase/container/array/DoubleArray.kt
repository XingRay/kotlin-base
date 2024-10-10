package com.github.xingray.kotlinbase.container.array

import com.github.xingray.kotlinbase.container.interfaces.series.DoubleSeries
import java.util.function.Consumer

class DoubleArray : Iterable<Double?>, DoubleSeries {
    private val array: kotlin.DoubleArray?

    constructor(array: kotlin.DoubleArray?) {
        this.array = array
    }

    constructor(length: Int) {
        array = kotlin.DoubleArray(length)
    }

    override fun get(index: Int): Double {
        return array!![index]
    }

    override fun length(): Int {
        return array!!.size
    }

    fun set(index: Int, value: Double) {
        array!![index] = value
    }

    fun toList(): List<Double> {
        val length = length()
        if (length == 0) {
            return emptyList()
        }
        val list = ArrayList<Double>(length)
        for (value in array!!) {
            list.add(value)
        }
        return list
    }

    override fun iterator(): Iterator<Double> {
        return ArrayIterator(this)
    }

    fun forEach(action: Consumer<in Double>) {
        if (array == null || array.size == 0) {
            return
        }
        for (value in array) {
            action.accept(value)
        }
    }

    private class ArrayIterator(private val array: DoubleArray) : Iterator<Double> {
        private var index = 0

        override fun hasNext(): Boolean {
            return array.length() > 0 && index < array.length()
        }

        override fun next(): Double {
            val value = array.get(index)
            index++
            return value
        }
    }

    companion object {
        fun of(vararg values: Double): DoubleArray? {
            if (values == null) {
                return null
            }
            return DoubleArray(values)
        }
    }
}
