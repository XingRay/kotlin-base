package com.github.xingray.kotlinbase.interfaces.predicate.index

fun interface BooleanIndexPredicate {
    fun test(value: Boolean, index: Int): Boolean
}
