package com.github.xingray.kotlinbase.interfaces.predicate.index

fun interface DoubleIndexPredicate {
    fun test(value: Double, index: Int): Boolean
}
