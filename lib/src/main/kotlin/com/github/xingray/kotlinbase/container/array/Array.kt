package com.github.xingray.kotlinbase.container.array

import com.github.xingray.kotlinbase.container.interfaces.series.Series
import java.util.*
import java.util.function.Consumer

class Array<T> : Iterable<T>, Series<T> {
    private val array: kotlin.Array<Any?>

    constructor(array: kotlin.Array<Any?>) {
        this.array = array
    }

    constructor(length: Int) {
        array = arrayOfNulls(length)
    }

    override fun get(index: Int): T {
        return array[index] as T
    }

    override fun length(): Int {
        return array.size
    }

    fun set(index: Int, t: T) {
        array[index] = t
    }

    fun toList(): List<T> {
        val length = length()
        if (length == 0) {
            return emptyList()
        }
        return ArrayList(Arrays.asList(*array) as Collection<T>)
    }

    override fun iterator(): Iterator<T> {
        return ArrayIterator(this)
    }

    override fun forEach(action: Consumer<in T>) {
        for (t in array) {
            action.accept(t as T)
        }
    }

    private class ArrayIterator<T>(private val array: Array<T>) : Iterator<T> {
        private var index = 0

        override fun hasNext(): Boolean {
            return array.length() > 0 && index < array.length()
        }

        override fun next(): T {
            val t: T = array.get(index)
            index++
            return t
        }
    }

    companion object {
        inline fun <reified E> of(iterator: Iterator<E>): Array<E> {
            val c: Collection<E>

            if (iterator is Collection<*>) {
                c = iterator as Collection<E>
            } else {
                val list = ArrayList<E>()
                while (iterator.hasNext()) {
                    list.add(iterator.next())
                }
                c = list
            }
            return Array(c.toTypedArray())
        }
    }
}
