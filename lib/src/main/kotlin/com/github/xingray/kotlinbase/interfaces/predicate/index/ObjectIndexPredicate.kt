package com.github.xingray.kotlinbase.interfaces.predicate.index

fun interface ObjectIndexPredicate<T> {
    fun test(value: T, index: Int): Boolean
}
