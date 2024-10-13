package com.github.xingray.kotlinbase.ext.primary

fun Boolean.trueOrNull(): Boolean? {
    return if (this) {
        true
    } else {
        null
    }
}

fun Boolean.toInt(): Int {
    return if (this) {
        1
    } else {
        0
    }
}