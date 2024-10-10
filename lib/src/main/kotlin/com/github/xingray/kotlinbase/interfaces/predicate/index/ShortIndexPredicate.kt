package com.github.xingray.kotlinbase.interfaces.predicate.index

fun interface ShortIndexPredicate {
    fun test(value: Short, index: Int): Boolean
}
