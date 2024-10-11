package com.github.xingray.kotlinbase.graphic

fun Size.rotate(degree: Int): Size {
    return if (degree == 90 || degree == 270) {
        Size(height, width)
    } else {
        this
    }
}