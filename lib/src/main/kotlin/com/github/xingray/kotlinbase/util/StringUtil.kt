package com.github.xingray.kotlinbase.util

import java.text.NumberFormat
import java.util.function.BiFunction
import java.util.function.Function
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.max

object StringUtil {
    fun isEmpty(s: String?): Boolean {
        return s == null || s.isBlank() || s.trim { it <= ' ' }.isEmpty()
    }

    fun hasText(s: String?): Boolean {
        return (s != null && !s.isEmpty() && containsText(s))
    }

    fun isEmpty(s: CharSequence?): Boolean {
        return s == null || s.isEmpty()
    }

    fun hasText(charSequence: CharSequence?): Boolean {
        return charSequence != null && !charSequence.isEmpty() && containsText(charSequence)
    }


    private fun containsText(str: CharSequence): Boolean {
        val strLen = str.length
        for (i in 0 until strLen) {
            if (!Character.isWhitespace(str[i])) {
                return true
            }
        }
        return false
    }


    // ========================= Array =================================//
    fun <T> toString(array: Array<T>?, sep: String = ",", mapper: Function<T, String?>? = null): String {
        if (array.isNullOrEmpty()) {
            return ""
        }

        val builder = StringBuilder()
        var isFirst = true
        for (o in array) {
            val s = if (o == null) {
                null
            } else if (mapper == null) {
                o.toString()
            } else {
                mapper.apply(o)
            }

            if (isEmpty(s)) {
                continue
            }
            if (!isFirst) {
                builder.append(sep)
            }
            isFirst = false
            builder.append(s)
        }

        return builder.toString()
    }


    //  ========================== Iterable ===============================//
    fun <T> toString(iterable: Iterable<T>?, sep: String?, mapper: Function<T, String?>?): String {
        if (iterable == null) {
            return ""
        }

        val builder = StringBuilder()
        var isFirst = true
        for (o in iterable) {
            val s = if (o == null) null else if (mapper == null) o.toString() else mapper.apply(o)
            if (isEmpty(s)) {
                continue
            }
            if (!isFirst) {
                builder.append(sep)
            }
            isFirst = false
            builder.append(s)
        }

        return builder.toString()
    }

    fun <T> toString(iterable: Iterable<T>?, sep: String?): String {
        return toString(iterable, sep, null)
    }

    fun toString(iterable: Iterable<*>?): String {
        return toString(iterable, ",")
    }


    //  ========================== Map ===============================//
    fun <K, V> toString(map: Map<K, V>?, sep: String?, mapper: BiFunction<K, V, String?>?): String {
        if (map == null || map.isEmpty()) {
            return ""
        }

        val builder = StringBuilder()
        var isFirst = true
        for ((key, value) in map) {
            val s = if (mapper == null) key.toString() + ":" + value else mapper.apply(key, value)!!
            if (isEmpty(s)) {
                continue
            }
            if (!isFirst) {
                builder.append(sep)
            }
            isFirst = false
            builder.append(s)
        }

        return builder.toString()
    }

    fun <K, V> toString(map: Map<K, V>?, sep: String?): String {
        return toString(map, sep, null)
    }

    fun <K, V> toString(map: Map<K, V>?): String {
        return toString(map, ",")
    }


    fun toString(array: BooleanArray?, sep: String?): String {
        if (array == null || array.size == 0) {
            return ""
        }

        val builder = StringBuilder()
        var isFirst = true
        for (o in array) {
            val s = o.toString()
            if (isEmpty(s)) {
                continue
            }
            if (!isFirst) {
                builder.append(sep)
            }
            isFirst = false
            builder.append(s)
        }

        return builder.toString()
    }

    fun toString(array: BooleanArray?): String {
        return toString(array, ",")
    }

    fun toString(array: ByteArray?): String {
        return toString(array, ",")
    }

    fun toString(array: ByteArray?, sep: String?): String {
        if (array == null || array.size == 0) {
            return ""
        }

        val builder = StringBuilder()
        var isFirst = true
        for (o in array) {
            val s = o.toString()
            if (isEmpty(s)) {
                continue
            }
            if (!isFirst) {
                builder.append(sep)
            }
            isFirst = false
            builder.append(s)
        }

        return builder.toString()
    }

    fun toString(array: ShortArray?): String {
        return toString(array, ",")
    }

    fun toString(array: ShortArray?, sep: String?): String {
        if (array == null || array.size == 0) {
            return ""
        }

        val builder = StringBuilder()
        var isFirst = true
        for (o in array) {
            val s = o.toString()
            if (isEmpty(s)) {
                continue
            }
            if (!isFirst) {
                builder.append(sep)
            }
            isFirst = false
            builder.append(s)
        }

        return builder.toString()
    }

    fun toString(array: CharArray?): String {
        return toString(array, ",")
    }

    fun toString(array: CharArray?, sep: String?): String {
        if (array == null || array.size == 0) {
            return ""
        }

        val builder = StringBuilder()
        var isFirst = true
        for (o in array) {
            val s = o.toString()
            if (isEmpty(s)) {
                continue
            }
            if (!isFirst) {
                builder.append(sep)
            }
            isFirst = false
            builder.append(s)
        }

        return builder.toString()
    }

    fun toString(array: IntArray?): String {
        return toString(array, ",")
    }

    fun toString(array: IntArray?, sep: String?): String {
        if (array == null || array.size == 0) {
            return ""
        }

        val builder = StringBuilder()
        var isFirst = true
        for (o in array) {
            val s = o.toString()
            if (isEmpty(s)) {
                continue
            }
            if (!isFirst) {
                builder.append(sep)
            }
            isFirst = false
            builder.append(s)
        }

        return builder.toString()
    }

    fun toString(array: LongArray?): String {
        return toString(array, ",")
    }

    fun toString(array: LongArray?, sep: String?): String {
        if (array == null || array.size == 0) {
            return ""
        }

        val builder = StringBuilder()
        var isFirst = true
        for (o in array) {
            val s = o.toString()
            if (isEmpty(s)) {
                continue
            }
            if (!isFirst) {
                builder.append(sep)
            }
            isFirst = false
            builder.append(s)
        }

        return builder.toString()
    }

    fun toString(array: FloatArray?): String {
        return toString(array, ",")
    }

    fun toString(array: FloatArray?, sep: String?): String {
        if (array == null || array.size == 0) {
            return ""
        }

        val builder = StringBuilder()
        var isFirst = true
        for (o in array) {
            val s = o.toString()
            if (isEmpty(s)) {
                continue
            }
            if (!isFirst) {
                builder.append(sep)
            }
            isFirst = false
            builder.append(s)
        }

        return builder.toString()
    }

    fun toString(array: DoubleArray?): String {
        return toString(array, ",")
    }

    fun toString(array: DoubleArray?, sep: String?): String {
        if (array == null || array.size == 0) {
            return ""
        }

        val builder = StringBuilder()
        var isFirst = true
        for (o in array) {
            val s = o.toString()
            if (isEmpty(s)) {
                continue
            }
            if (!isFirst) {
                builder.append(sep)
            }
            isFirst = false
            builder.append(s)
        }

        return builder.toString()
    }

    fun toString(o: Any?): String? {
        return toString(o, null)
    }

    fun toString(value: Any?, defaultValue: String?): String? {
        if (value == null) {
            return defaultValue
        }
        if (value is String) {
            return value
        }
        return value.toString()
    }

    fun toString(value: Double): String {
        val nf = NumberFormat.getInstance()
        nf.isGroupingUsed = false
        return nf.format(value)
    }

    fun toString(value: Float): String {
        val nf = NumberFormat.getInstance()
        nf.isGroupingUsed = false
        return nf.format(value.toDouble())
    }

    fun toLongs(s: String, sep: String): Array<Long?>? {
        if (isEmpty(s)) {
            return null
        }
        val split = s.split(sep.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val length = split.size
        val longs = arrayOfNulls<Long>(length)

        for (i in 0 until length) {
            longs[i] = NumberUtil.toLong(split[i])
        }
        return longs
    }

    fun toIntegers(s: String, sep: String): Array<Int?>? {
        if (isEmpty(s)) {
            return null
        }
        val split = s.split(sep.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val length = split.size
        val integers = arrayOfNulls<Int>(length)

        for (i in 0 until length) {
            integers[i] = NumberUtil.toInteger(split[i])
        }
        return integers
    }

    fun toLongValues(s: String, sep: String): LongArray {
        val split = s.split(sep.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val length = split.size
        val longs = LongArray(length)

        for (i in 0 until length) {
            longs[i] = NumberUtil.toLongValue(split[i])
        }
        return longs
    }

    fun toInts(s: String, sep: String): IntArray {
        val split = s.split(sep.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val length = split.size
        val ints = IntArray(length)

        for (i in 0 until length) {
            ints[i] = NumberUtil.toIntValue(split[i], 0)
        }
        return ints
    }

    fun cutIfStartWith(raw: String, start: String): String {
        var raw = raw
        if (raw.startsWith(start)) {
            raw = raw.substring(start.length)
        }
        return raw
    }

    fun cutIfEndWith(raw: String, end: String): String {
        var raw = raw
        if (raw.endsWith(end)) {
            raw = raw.substring(0, raw.length - end.length)
        }
        return raw
    }

    fun cutBefore(raw: String, content: String?): String {
        var raw = raw
        val index = raw.indexOf(content!!)
        if (index >= 0) {
            raw = raw.substring(0, index)
        }
        return raw
    }

    fun cutAfter(raw: String, content: String): String {
        var raw = raw
        val index = raw.indexOf(content)
        if (index >= 0) {
            raw = raw.substring(index + content.length)
        }
        return raw
    }

    fun captain(str: String): String {
        val ch = str.toCharArray()
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (ch[0].code - 32).toChar()
        }
        return String(ch)
    }

    fun getIntString(value: Int, length: Int): String {
        val indexString = value.toString()
        if (length <= 0) {
            return indexString
        }
        if (value < 0) {
            return indexString
        }
        val lengthDiff = length - indexString.length
        return "0".repeat(max(0.0, lengthDiff.toDouble()).toInt()) + indexString
    }

    fun getAlignIntString(value: Int, maxValue: Int): String {
        return getIntString(value, floor(log10(maxValue.toDouble())).toInt() + 1)
    }
}