package com.nanosecond.makeup.kotlin

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