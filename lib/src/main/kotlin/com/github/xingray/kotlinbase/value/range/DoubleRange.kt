package com.github.xingray.kotlinbase.value.range

import java.util.*

/**
 * Description : 范围
 */
class DoubleRange(var start: Double, var end: Double) {
    override fun toString(): String {
        return ("\"Range\": {"
                + "\"from\": \"" + start
                + ", \"to\": \"" + end
                + '}')
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as DoubleRange
        return java.lang.Double.compare(that.start, start) == 0 &&
                java.lang.Double.compare(that.end, end) == 0
    }

    override fun hashCode(): Int {
        return Objects.hash(start, end)
    }
}
