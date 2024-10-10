package com.github.xingray.kotlinbase.container.container

import com.github.xingray.kotlinbase.container.interfaces.container.Container
import java.util.*
import java.util.function.BiFunction
import java.util.function.Predicate

class DoubleArrayContainer(
    private val array: DoubleArray
) : Container<Int, Double> {

    override fun get(index: Int): Double? {
        return array[index]
    }

    override fun set(index: Int, value: Double) {
        array[index] = value
    }

    override fun size(): Int {
        return array.size
    }

    override fun toList(): List<Double> {
        val list = ArrayList<Double>(array.size)
        for (value in array) {
            list.add(value)
        }
        return list
    }


    override fun toArray(): Array<Double> {
        val result = mutableListOf<Double>()
        var i = 0
        val arrayLength = array.size
        while (i < arrayLength) {
            result.add(array[i])
            i++
        }
        return result.toTypedArray()
    }

    override fun toMap(): Map<Int, Double> {
        val map: MutableMap<Int, Double> = TreeMap(Comparator.naturalOrder())
        var i = 0
        val arrayLength = array.size
        while (i < arrayLength) {
            map[i] = array[i]
            i++
        }
        return map
    }

    override fun toSet(): Set<Double> {
        if (isEmpty) {
            return emptySet()
        }
        val set = HashSet<Double>()
        for (v in array) {
            set.add(v)
        }
        return set
    }

    override fun find(predicate: Predicate<Double>): Double? {
        for (v in array) {
            if (predicate.test(v)) {
                return v
            }
        }
        return null
    }

    override fun findAll(predicate: Predicate<Double>): Container<Int, Double> {
        var list: MutableList<Double>? = null
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

        val array = DoubleArray(list.size)
        var i = 0
        val listSize = list.size
        while (i < listSize) {
            array[i] = list[i]
            i++
        }

        return DoubleArrayContainer(array)
    }

    override fun merge(container: Container<Int, Double>, biConsumer: BiFunction<Double, Double, Double>): Container<Int, Double> {
        if (container.isEmpty) {
            return DoubleArrayContainer(array)
        }
        val target = DoubleArray(size() + container.size())
        System.arraycopy(array, 0, target, 0, array.size)
        val containerArray: Array<Double> = container.toArray()
        val mergeArray = DoubleArray(containerArray.size)
        var i = 0
        val containerArrayLength = containerArray.size
        while (i < containerArrayLength) {
            val value = containerArray[i]
            mergeArray[i] = value
            i++
        }
        System.arraycopy(mergeArray, 0, target, array.size, mergeArray.size)
        return DoubleArrayContainer(target)
    }

    override fun copy(): Container<Int, Double> {
        return DoubleArrayContainer(array)
    }
}
