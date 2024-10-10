package com.github.xingray.kotlinbase.container.container

import com.github.xingray.kotlinbase.container.interfaces.container.Container
import java.util.*
import java.util.function.BiFunction
import java.util.function.Predicate

class SetContainer<T>(set: MutableSet<T>) : Container<T, T> {
    private val set: MutableSet<T>

    init {
        this.set = set
    }

    override fun get(index: T): T? {
        return if (set.contains(index)) index else null
    }

    override fun set(index: T, value: T) {
        set.add(value)
    }

    override fun size(): Int {
        return set.size
    }

    override fun toList(): List<T> {
        return java.util.List.copyOf(set)
    }

    override fun toArray(): Array<T> {
        val t = find { obj: T -> Objects.nonNull(obj) }
        if (t == null) {
            return emptyArray<Any>() as Array<T>
        }
        val size = set.size
        // noinspection unchecked
//        val array = java.lang.reflect.Array.newInstance(t.javaClass, size) as Array<T>
//        return set.toArray(array)
        return (set as Set<Any>).toTypedArray() as Array<T>
    }

    override fun toMap(): Map<T, T> {
        val map = HashMap<T, T>(set.size)
        for (t in set) {
            map[t] = t
        }
        return map
    }

    override fun toSet(): Set<T> {
        return java.util.Set.copyOf(set)
    }

    override fun find(predicate: Predicate<T>): T? {
        for (t in set) {
            if (predicate.test(t)) {
                return t
            }
        }
        return null
    }

    override fun findAll(predicate: Predicate<T>): Container<T, T> {
        var result: MutableSet<T>? = null
        for (value in set) {
            if (predicate.test(value)) {
                if (result == null) {
                    result = HashSet()
                }
                result.add(value)
            }
        }
        if (result == null) {
            return EmptyContainer.getInstance()
        }
        return SetContainer(result)
    }

    override fun merge(container: Container<T, T>, biConsumer: BiFunction<T, T, T>): Container<T, T> {
        if (container.isEmpty) {
            return SetContainer(set)
        }
        val target: Set<T> = container.toSet()
        val merged: MutableSet<T> = HashSet(set)
        merged.addAll(target)

        return SetContainer(merged)
    }

    override fun copy(): Container<T, T> {
        return SetContainer(set)
    }

    override fun toString(): String {
        return "SetContainer{" +
                "set=" + set +
                '}'
    }
}
