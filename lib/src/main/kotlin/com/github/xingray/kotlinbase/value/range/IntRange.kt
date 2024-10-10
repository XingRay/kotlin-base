package com.github.xingray.kotlinbase.value.range

import java.util.*

/**
 * Description : 范围
 */
class IntRange(var start: Int, var end: Int) {
    override fun toString(): String {
        return ("\"Range\": {"
                + "\"from\": \"" + start
                + ", \"to\": \"" + end
                + '}')
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val intRange = o as IntRange
        return start == intRange.start &&
                end == intRange.end
    }

    override fun hashCode(): Int {
        return Objects.hash(start, end)
    }
}
