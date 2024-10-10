package com.github.xingray.kotlinbase.container.container

import com.github.xingray.kotlinbase.container.interfaces.container.Container
import java.util.*
import java.util.function.BiFunction
import java.util.function.Predicate

class ArrayContainer<T>(
    private val array: Array<T>
) : Container<Int, T> {

    override fun get(index: Int): T? {
        if (index < 0 || index >= size()) {
            return null
        }
        return array[index]
    }

    override fun set(index: Int, t: T) {
        array[index] = t
    }

    override fun size(): Int {
        return array.size
    }

    override fun toList(): List<T> {
        return array.toList()
    }

    override fun toArray(): Array<T> {
        return array.copyOf()
    }

    override fun toMap(): Map<Int, T> {
        val map = mutableMapOf<Int, T>()
        for (i in array.indices) {
            map[i] = array[i]
        }
        return map
    }

    override fun toSet(): Set<T> {
        return HashSet(Arrays.asList(*array))
    }

    override fun find(predicate: Predicate<T>): T? {
        for (t in array) {
            if (predicate.test(t)) {
                return t
            }
        }
        return null
    }

    override fun findAll(predicate: Predicate<T>): Container<Int, T> {
        var list: MutableList<T>? = null
        for (t in array) {
            if (predicate.test(t)) {
                if (list == null) {
                    list = ArrayList()
                }
                list.add(t)
            }
        }
        if (list == null) {
            return EmptyContainer.getInstance()
        }
        // noinspection unchecked
        val array = java.lang.reflect.Array.newInstance((list[0] as Any).javaClass, list.size) as Array<T>
        return ArrayContainer(array)
    }


    override fun merge(container: Container<Int, T>, biConsumer: BiFunction<T, T, T>): Container<Int, T> {
        if (container.size() == 0) {
            return ArrayContainer(array)
        }

        val cls = (this.array[0] as Any).javaClass
        val target = java.lang.reflect.Array.newInstance(cls, size() + container.size()) as Array<T>
        System.arraycopy(array, 0, target, 0, array.size)
        val mergeArray: Array<T> = container.toArray()
        System.arraycopy(mergeArray, 0, target, array.size, mergeArray.size)
        return ArrayContainer(target)
    }

    override fun copy(): Container<Int, T> {
        return ArrayContainer(array)
    }

    override fun toString(): String {
        return "ArrayContainer{" +
                "array=" + array.contentToString() +
                '}'
    }
}