package com.github.xingray.kotlinbase.interfaces.predicate.index

fun interface LongIndexPredicate {
    fun test(value: Long, index: Int): Boolean
}
