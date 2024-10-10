package com.github.xingray.kotlinbase.container.container

import com.github.xingray.kotlinbase.container.interfaces.container.Container
import java.util.*
import java.util.function.BiFunction
import java.util.function.Predicate

class ListContainer<T>(list: List<T>) : Container<Int, T> {
    private val list: MutableList<T> = list.toMutableList()

    override fun get(index: Int): T? {
        return list[index]
    }

    override fun set(index: Int, value: T) {
        list[index] = value
    }

    override fun size(): Int {
        return list.size
    }

    override fun toList(): List<T> {
        return ArrayList(list)
    }

    override fun toArray(): Array<T> {
        val t = find { obj: T -> Objects.nonNull(obj) }
        if (t == null) {
            return emptyArray<Any>() as Array<T>
        }

        val size = list.size
        // noinspection unchecked
//        val array = java.lang.reflect.Array.newInstance(t.javaClass, size) as Array<T>
        return (list as List<Any>).toTypedArray() as Array<T>
    }

    override fun toMap(): Map<Int, T> {
        val map = TreeMap<Int, T>(Comparator.naturalOrder())
        for (i in list.indices) {
            map[i] = list[i]
        }
        return map
    }

    override fun toSet(): Set<T> {
        return java.util.Set.copyOf(list)
    }

    override fun find(predicate: Predicate<T>): T? {
        for (t in list) {
            if (predicate.test(t)) {
                return t
            }
        }
        return null
    }

    override fun findAll(predicate: Predicate<T>): Container<Int, T> {
        var result: MutableList<T>? = null
        for (t in list) {
            if (predicate.test(t)) {
                if (result == null) {
                    result = ArrayList()
                }
                result.add(t)
            }
        }
        if (result == null) {
            return EmptyContainer.getInstance()
        }
        return ListContainer(result)
    }

    override fun merge(container: Container<Int, T>, biConsumer: BiFunction<T, T, T>): Container<Int, T> {
        if (container.isEmpty) {
            return ListContainer(list)
        }
        val target: List<T> = container.toList()

        val merged: MutableList<T> = ArrayList(list.size + target.size)
        merged.addAll(list)
        merged.addAll(target)
        return ListContainer(merged)
    }

    override fun copy(): Container<Int, T> {
        return ListContainer(list)
    }

    override fun toString(): String {
        return "ListContainer{" +
                "list=" + list +
                '}'
    }
}
