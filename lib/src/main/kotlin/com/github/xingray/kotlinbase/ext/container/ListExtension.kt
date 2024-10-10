package com.github.xingray.kotlinbase.ext.container

fun List<Any>?.hasElement(): Boolean {
    if (this == null) {
        return false
    }
    return !isEmpty()
}

fun List<Any>?.ifNullOrEmpty(task: () -> Unit) {
    if (this == null || this.isEmpty()) {
        task.invoke()
    }
}

fun <T> List<T>?.emptyToNull(): List<T>? {
    return if (this.isNullOrEmpty()) {
        null
    } else {
        this
    }
}

inline fun <T> List<T>.indexOfFirstWithIndex(predicate: (T, Int) -> Boolean): Int {
    var index = 0
    for (item in this) {
        if (predicate(item, index))
            return index
        index++
    }
    return -1
}

fun <T> List<T>.contentToString(stringMapper: ((T) -> String)? = null): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append('[')
    for ((index, item) in this.withIndex()) {
        if (index > 0) {
            stringBuilder.append(',').append(' ')
        }
        stringBuilder.append(stringMapper?.invoke(item) ?: item.toString())
    }
    stringBuilder.append(']')
    return stringBuilder.toString()
}

fun <T> List<T>.contentToStringWithBuilder(stringMapper: ((T, StringBuilder) -> Unit)? = null): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append('[')
    for ((index, item) in this.withIndex()) {
        if (index > 0) {
            stringBuilder.append(',').append(' ')
        }
        stringMapper?.invoke(item, stringBuilder) ?: stringBuilder.append(item.toString())
    }
    stringBuilder.append(']')
    return stringBuilder.toString()
}

fun <P, A : P, B : P> List<P>.toPairs(aClass: Class<A>, bClass: Class<B>, isPair: (A, B) -> Boolean): List<Pair<A?, B?>> {
    val pairs = mutableListOf<Pair<A?, B?>>()

    val aList = mutableListOf<A>()
    val bList = mutableListOf<B>()

    // 按类型将 connections 分类到 aConnections 和 bConnections 列表中
    for (p in this) {
        when {
            aClass.isInstance(p) -> aList.add(p as A)
            bClass.isInstance(p) -> bList.add(p as B)
        }
    }

    // 匹配A和B
    for (a in aList) {
        val index = bList.indexOfFirst { isPair(a, it) }
        if (index < 0) {
            pairs.add(Pair(a, null))
        } else {
            pairs.add(Pair(a, bList[index]))
            bList.removeAt(index)
        }
    }

    // 处理没有匹配到的B
    for (b in bList) {
        pairs.add(Pair(null, b))
    }

    return pairs
}