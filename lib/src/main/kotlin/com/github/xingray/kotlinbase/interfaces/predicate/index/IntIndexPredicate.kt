package com.github.xingray.kotlinbase.interfaces.predicate.index

fun interface IntIndexPredicate {
    fun test(value: Int, index: Int): Boolean
}
