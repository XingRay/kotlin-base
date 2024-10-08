package com.github.xingray.uiautomatorproxy.kotlin

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