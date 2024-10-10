package com.github.xingray.kotlinbase.container.container

import com.github.xingray.kotlinbase.container.interfaces.container.Container
import java.util.function.BiFunction
import java.util.function.Predicate

class EmptyArrayContainer<Index, Value>() : Container<Index, Value> {

    override fun get(index: Index): Value? {
        return null
    }

    override fun set(index: Index, value: Value) {
    }

    override fun size(): Int {
        return 0
    }

    override fun toList(): List<Value> {
        return emptyList()
    }

    override fun toArray(): Array<Value> {
        return arrayOfNulls<Any>(0) as Array<Value>
    }

    override fun toMap(): Map<Index, Value> {
        return emptyMap()
    }

    override fun toSet(): Set<Value> {
        return emptySet()
    }

    override fun find(predicate: Predicate<Value>): Value? {
        return null
    }

    override fun findAll(predicate: Predicate<Value>): Container<Index, Value> {
        return this
    }

    override fun merge(container: Container<Index, Value>, biConsumer: BiFunction<Value, Value, Value>): Container<Index, Value> {
        return container.copy()
    }

    override fun copy(): Container<Index, Value> {
        return this
    }
}
