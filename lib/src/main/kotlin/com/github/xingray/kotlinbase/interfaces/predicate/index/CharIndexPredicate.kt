package com.github.xingray.kotlinbase.interfaces.predicate.index

fun interface CharIndexPredicate {
    fun test(value: Char, index: Int): Boolean
}
