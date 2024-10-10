package com.github.xingray.kotlinbase.container.interfaces.container

import java.util.function.BiFunction
import java.util.function.Predicate

interface Container<Index, Value> {
    fun get(index: Index): Value?

    fun set(index: Index, value: Value)

    fun size(): Int

    fun hasElement(): Boolean {
        return size() > 0
    }

    val isEmpty: Boolean
        get() = size() == 0

    fun toList(): List<Value>

    fun toArray(): Array<Value>

    fun toMap(): Map<Index, Value>

    fun toSet(): Set<Value>

    fun find(predicate: Predicate<Value>): Value?

    fun findAll(predicate: Predicate<Value>): Container<Index, Value>

    /**
     * 合并容器
     *
     * @param container  待合并的容器
     * @param biConsumer 两个值的合并策略，线性表的合并忽略这个策略，相当于addAll
     * @return 新的容器，包含两个容器合并后的结果
     */
    fun merge(container: Container<Index, Value>, biConsumer: BiFunction<Value, Value, Value>): Container<Index, Value>

    fun copy(): Container<Index, Value>
}