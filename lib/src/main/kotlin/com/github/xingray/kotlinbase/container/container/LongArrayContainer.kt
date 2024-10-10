package com.github.xingray.kotlinbase.container.container

import com.github.xingray.kotlinbase.container.interfaces.container.Container
import java.util.*
import java.util.function.BiFunction
import java.util.function.Predicate

class LongArrayContainer(array: LongArray) : Container<Int, Long> {
    private val array: LongArray

    init {
        this.array = array
    }

    override fun get(index: Int): Long? {
        return array[index]
    }

    override fun set(index: Int, value: Long) {
        array[index] = value
    }

    override fun size(): Int {
        return array.size
    }

    override fun toList(): List<Long> {
        val list = ArrayList<Long>(array.size)
        for (value in array) {
            list.add(value)
        }
        return list
    }

    override fun toArray(): Array<Long> {
        val result = mutableListOf<Long>()
        var i = 0
        val arrayLength = array.size
        while (i < arrayLength) {
            result.add(array[i])
            i++
        }
        return result.toTypedArray()
    }

    override fun toMap(): Map<Int, Long> {
        val map: MutableMap<Int, Long> = TreeMap(Comparator.naturalOrder())
        var i = 0
        val arrayLength = array.size
        while (i < arrayLength) {
            map[i] = array[i]
            i++
        }
        return map
    }

    override fun toSet(): Set<Long> {
        if (isEmpty) {
            return emptySet()
        }
        val set = HashSet<Long>()
        for (v in array) {
            set.add(v)
        }
        return set
    }

    override fun find(predicate: Predicate<Long>): Long? {
        for (v in array) {
            if (predicate.test(v)) {
                return v
            }
        }
        return null
    }

    override fun findAll(predicate: Predicate<Long>): Container<Int, Long> {
        var list: MutableList<Long>? = null
        for (value in array) {
            if (predicate.test(value)) {
                if (list == null) {
                    list = ArrayList()
                }
                list.add(value)
            }
        }
        if (list == null) {
            return EmptyContainer.getInstance()
        }

        val array = LongArray(list.size)
        var i = 0
        val listSize = list.size
        while (i < listSize) {
            array[i] = list[i]
            i++
        }

        return LongArrayContainer(array)
    }

    override fun merge(container: Container<Int, Long>, biConsumer: BiFunction<Long, Long, Long>): Container<Int, Long> {
        if (container.isEmpty) {
            return LongArrayContainer(array)
        }
        val target = LongArray(size() + container.size())
        System.arraycopy(array, 0, target, 0, array.size)
        val containerArray: Array<Long> = container.toArray()
        val mergeArray = LongArray(containerArray.size)
        var i = 0
        val containerArrayLength = containerArray.size
        while (i < containerArrayLength) {
            val value = containerArray[i]
            mergeArray[i] = value
            i++
        }
        System.arraycopy(mergeArray, 0, target, array.size, mergeArray.size)
        return LongArrayContainer(target)
    }

    override fun copy(): Container<Int, Long> {
        return LongArrayContainer(array)
    }
}
