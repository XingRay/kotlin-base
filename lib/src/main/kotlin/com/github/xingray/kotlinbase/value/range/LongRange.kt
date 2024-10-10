package com.github.xingray.kotlinbase.value.range

import java.util.*

/**
 * Description : 范围
 */
class LongRange(var start: Long, var end: Long) {
    override fun toString(): String {
        return ("\"Range\": {"
                + "\"from\": \"" + start
                + ", \"to\": \"" + end
                + '}')
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val longRange = o as LongRange
        return start == longRange.start &&
                end == longRange.end
    }

    override fun hashCode(): Int {
        return Objects.hash(start, end)
    }
}
