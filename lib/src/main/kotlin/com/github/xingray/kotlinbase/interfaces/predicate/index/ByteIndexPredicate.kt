package com.github.xingray.kotlinbase.interfaces.predicate.index

fun interface ByteIndexPredicate {
    fun test(value: Byte, index: Int): Boolean
}
