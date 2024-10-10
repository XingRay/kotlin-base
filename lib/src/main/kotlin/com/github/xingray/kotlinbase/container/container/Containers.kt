package com.github.xingray.kotlinbase.container.container

import com.github.xingray.kotlinbase.container.interfaces.container.Container

object Containers {
    fun <K, V> of(map: Map<K, V>?): Container<K, V> {
        if (map.isNullOrEmpty()) {
            return empty()
        }
        return MapContainer(map)
    }

    fun <T> of(list: List<T>?): Container<Int, T> {
        if (list.isNullOrEmpty()) {
            return empty()
        }
        return ListContainer(list)
    }

    fun <T> of(array: Array<T>?): Container<Int, T> {
        if (array.isNullOrEmpty()) {
            return empty()
        }
        val cls = findClass(array) ?: throw IllegalArgumentException()
        return of(cls, array)
    }

    fun <T> of(cls: Class<T>?, array: Array<T>?): Container<Int, T> {
        if (array.isNullOrEmpty()) {
            return empty()
        }
        return ArrayContainer(array)
    }

    fun <T> ofValues(vararg values: T): Container<Int, T> {
        if (values.isEmpty()) {
            return empty()
        }
        val cls = findClass<T>(values as Array<T>) ?: throw IllegalArgumentException()
        return ofValues(cls, *values)
    }

    fun <T> ofValues(cls: Class<T>, vararg values: T): Container<Int, T> {
        if (values.isEmpty()) {
            return empty()
        }
        return ArrayContainer(values) as Container<Int, T>
    }

    fun <K, V> empty(): Container<K, V> {
        return EmptyContainer.getInstance()
    }

    fun <T> findClass(array: Array<T>): Class<T>? {
        for (t in array) {
            if (t != null) {
                // noinspection unchecked
                return t.javaClass as Class<T>
            }
        }
        return null
    }
}
