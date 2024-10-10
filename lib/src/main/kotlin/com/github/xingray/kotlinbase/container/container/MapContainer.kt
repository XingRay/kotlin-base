package com.github.xingray.kotlinbase.container.container

import com.github.xingray.kotlinbase.container.interfaces.container.Container
import java.util.*
import java.util.function.BiFunction
import java.util.function.Predicate

class MapContainer<K, V>(map: Map<K, V>) : Container<K, V> {
    private val map: MutableMap<K, V>

    init {
        this.map = map.toMutableMap()
    }

    override fun get(k: K): V? {
        return map[k]
    }

    override fun set(k: K, v: V) {
        map[k] = v
    }

    override fun size(): Int {
        return map.size
    }

    override fun toList(): List<V> {
        return ArrayList(map.values)
    }

    override fun toArray(): Array<V> {
        val t = find { obj: V -> Objects.nonNull(obj) }
        if (t == null) {
            return emptyArray<Any>() as Array<V>
        }

        val size = map.size
        // noinspection unchecked
//        val array = java.lang.reflect.Array.newInstance(t.javaClass, size) as Array<V>
//        return map.values.toArray(array)
        return (map.values as List<Any>).toTypedArray() as Array<V>
    }


    override fun toMap(): Map<K, V> {
        return java.util.Map.copyOf(map)
    }

    override fun toSet(): Set<V> {
        return java.util.Set.copyOf(map.values)
    }

    override fun find(predicate: Predicate<V>): V? {
        val entries: Set<Map.Entry<K, V>> = map.entries
        for ((_, value) in entries) {
            if (predicate.test(value)) {
                return value
            }
        }
        return null
    }

    override fun findAll(predicate: Predicate<V>): Container<K, V> {
        var result: MutableMap<K, V>? = null
        for ((key, value) in map) {
            if (predicate.test(value)) {
                if (result == null) {
                    result = HashMap()
                }
                result[key] = value
            }
        }
        if (result == null) {
            return EmptyContainer.getInstance()
        }
        return MapContainer(result)
    }

    override fun merge(container: Container<K, V>, biConsumer: BiFunction<V, V, V>): Container<K, V> {
        if (container.isEmpty) {
            return MapContainer(map)
        }
        val merged = HashMap(map)
        val target: Map<K, V> = container.toMap()
        for ((key, value) in target) {
            val mergedValue = merged[key]
            if (mergedValue == null) {
                merged[key] = value
            } else {
                merged[key] = biConsumer.apply(mergedValue, value)
            }
        }
        return MapContainer(merged)
    }

    override fun copy(): Container<K, V> {
        return MapContainer(map)
    }

    override fun toString(): String {
        return "MapContainer{" +
                "map=" + map +
                '}'
    }
}
