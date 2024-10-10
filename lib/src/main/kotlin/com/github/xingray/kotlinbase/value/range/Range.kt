package com.github.xingray.kotlinbase.value.range

import java.util.*

class Range<T> {
    private var start: T? = null
    private var end: T? = null

    constructor()

    constructor(start: T, end: T) {
        this.start = start
        this.end = end
    }

    fun getStart(): T? {
        return start
    }

    fun setStart(start: T) {
        this.start = start
    }

    fun getEnd(): T? {
        return end
    }

    fun setEnd(end: T) {
        this.end = end
    }

    override fun toString(): String {
        return "Range{" +
                "start=" + start +
                ", end=" + end +
                '}'
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val range = o as Range<*>
        return start == range.start && end == range.end
    }

    override fun hashCode(): Int {
        return Objects.hash(start, end)
    }
}
