package com.github.xingray.kotlinbase.util

object NumberUtil {
    private fun isEmpty(s: CharSequence?): Boolean {
        return s == null || s.toString().isEmpty()
    }


    // ==================== Boolean =======================//
    fun toBooleanValue(value: Any?): Boolean {
        return toBooleanValue(value, false)
    }

    fun toBooleanValue(o: Any?, defaultValue: Boolean): Boolean {
        var value = defaultValue
        if (o == null) {
            return value
        }

        if (o is Boolean) {
            return o
        }
        var s = if (o is String) {
            o as String
        } else {
            o.toString()
        }
        s = s.trim { it <= ' ' }
        if (isEmpty(s)) {
            return value
        }
        value = s.toBoolean()

        return value
    }

    fun toBoolean(o: Any?): Boolean? {
        return toBoolean(o, null)
    }

    fun toBoolean(o: Any?, defaultValue: Boolean?): Boolean? {
        var value = defaultValue
        if (o == null) {
            return value
        }

        if (o is Boolean) {
            return o
        }
        var s = if (o is String) {
            o as String
        } else {
            o.toString()
        }
        s = s.trim { it <= ' ' }

        if (isEmpty(s)) {
            return value
        }

        value = s.toBoolean()

        return value
    }


    // ==================== Byte =======================//
    fun toByteValue(value: Any?): Byte {
        return toByteValue(value, 0.toByte())
    }

    fun toByteValue(o: Any?, defaultValue: Byte): Byte {
        var value = defaultValue
        if (o == null) {
            return value
        }

        if (o is Number) {
            return o.toByte()
        }
        var s = if (o is String) {
            o as String
        } else {
            o.toString()
        }
        s = s.trim { it <= ' ' }
        if (isEmpty(s)) {
            return value
        }

        try {
            value = s.toByte()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            try {
                value = s.toDouble().toInt().toByte()
            } catch (e1: NumberFormatException) {
                e1.printStackTrace()
            }
        }

        return value
    }

    fun toByte(o: Any?): Byte? {
        return toByte(o, null)
    }

    fun toByte(o: Any?, defaultValue: Byte?): Byte? {
        var value = defaultValue
        if (o == null) {
            return value
        }

        if (o is Byte) {
            return o
        }

        if (o is Number) {
            return o.toByte()
        }
        var s = if (o is String) {
            o as String
        } else {
            o.toString()
        }
        s = s.trim { it <= ' ' }

        if (isEmpty(s)) {
            return value
        }

        try {
            value = s.toByte()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            try {
                value = s.toDouble().toInt().toByte()
            } catch (e1: NumberFormatException) {
                e1.printStackTrace()
            }
        }

        return value
    }


    // ==================== Character =======================//
    fun toCharValue(value: Any?): Char {
        return toCharValue(value, 0.toChar())
    }

    fun toCharValue(o: Any?, defaultValue: Char): Char {
        var value = defaultValue
        if (o == null) {
            return value
        }

        if (o is Char) {
            return o
        }
        var s = if (o is String) {
            o as String
        } else {
            o.toString()
        }
        s = s.trim { it <= ' ' }
        if (isEmpty(s)) {
            return value
        }

        if (s.length > 0) {
            value = s[0]
        }

        return value
    }

    fun toCharacter(o: Any?): Char? {
        return toCharacter(o, null)
    }

    fun toCharacter(o: Any?, defaultValue: Char?): Char? {
        var value = defaultValue
        if (o == null) {
            return value
        }

        if (o is Char) {
            return o
        }
        var s = if (o is String) {
            o as String
        } else {
            o.toString()
        }
        s = s.trim { it <= ' ' }

        if (isEmpty(s)) {
            return value
        }

        if (s.length > 0) {
            value = s[0]
        }

        return value
    }


    // ==================== Short =======================//
    fun toShortValue(value: Any?): Short {
        return toShortValue(value, 0.toShort())
    }

    fun toShortValue(o: Any?, defaultValue: Short): Short {
        var value = defaultValue
        if (o == null) {
            return value
        }

        if (o is Number) {
            return o.toShort()
        }
        var s = if (o is String) {
            o as String
        } else {
            o.toString()
        }
        s = s.trim { it <= ' ' }
        if (isEmpty(s)) {
            return value
        }

        try {
            value = s.toShort()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            try {
                value = s.toDouble().toInt().toShort()
            } catch (e1: NumberFormatException) {
                e1.printStackTrace()
            }
        }

        return value
    }

    fun toShort(o: Any?): Short? {
        return toShort(o, null)
    }

    fun toShort(o: Any?, defaultValue: Short?): Short? {
        var value = defaultValue
        if (o == null) {
            return value
        }

        if (o is Short) {
            return o
        }

        if (o is Number) {
            return o.toShort()
        }
        var s = if (o is String) {
            o as String
        } else {
            o.toString()
        }
        s = s.trim { it <= ' ' }

        if (isEmpty(s)) {
            return value
        }

        try {
            value = s.toShort()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            try {
                value = s.toDouble().toInt().toShort()
            } catch (e1: NumberFormatException) {
                e1.printStackTrace()
            }
        }

        return value
    }


    // ==================== Integer =======================//
    fun toIntValue(value: Any?): Int {
        return toIntValue(value, 0)
    }

    fun toIntValue(o: Any?, defaultValue: Int): Int {
        var value = defaultValue
        if (o == null) {
            return value
        }

        if (o is Number) {
            return o.toInt()
        }
        var s = if (o is String) {
            o as String
        } else {
            o.toString()
        }
        s = s.trim { it <= ' ' }
        if (isEmpty(s)) {
            return value
        }

        try {
            value = s.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            try {
                value = s.toDouble().toInt()
            } catch (e1: NumberFormatException) {
                e1.printStackTrace()
            }
        }

        return value
    }

    fun toInteger(o: Any?): Int? {
        return toInteger(o, null)
    }

    fun toInteger(o: Any?, defaultValue: Int?): Int? {
        var value = defaultValue
        if (o == null) {
            return value
        }

        if (o is Int) {
            return o
        }

        if (o is Number) {
            return o.toInt()
        }
        var s = if (o is String) {
            o as String
        } else {
            o.toString()
        }
        s = s.trim { it <= ' ' }

        if (isEmpty(s)) {
            return value
        }

        try {
            value = s.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            try {
                value = s.toDouble().toInt()
            } catch (e1: NumberFormatException) {
                e1.printStackTrace()
            }
        }

        return value
    }


    // ==================== Long =======================//
    fun toLongValue(value: Any?): Long {
        return toLongValue(value, 0L)
    }

    fun toLongValue(o: Any?, defaultValue: Long): Long {
        var value = defaultValue
        if (o == null) {
            return value
        }

        if (o is Number) {
            return o.toLong()
        }
        var s = if (o is String) {
            o as String
        } else {
            o.toString()
        }
        s = s.trim { it <= ' ' }
        if (isEmpty(s)) {
            return value
        }

        try {
            value = s.toLong()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            try {
                value = s.toDouble().toLong()
            } catch (e1: NumberFormatException) {
                e1.printStackTrace()
            }
        }

        return value
    }

    fun toLong(o: Any?): Long? {
        return toLong(o, null)
    }

    fun toLong(o: Any?, defaultValue: Long?): Long? {
        var value = defaultValue
        if (o == null) {
            return value
        }

        if (o is Long) {
            return o
        }

        if (o is Number) {
            return o.toLong()
        }
        var s = if (o is String) {
            o as String
        } else {
            o.toString()
        }
        s = s.trim { it <= ' ' }

        if (isEmpty(s)) {
            return value
        }

        try {
            value = s.toLong()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            try {
                value = s.toDouble().toLong()
            } catch (e1: NumberFormatException) {
                e1.printStackTrace()
            }
        }

        return value
    }


    // ==================== Float =======================//
    fun toFloatValue(value: Any?): Float {
        return toFloatValue(value, 0f)
    }

    fun toFloatValue(o: Any?, defaultValue: Float): Float {
        var value = defaultValue
        if (o == null) {
            return value
        }

        if (o is Number) {
            return o.toFloat()
        }
        var s = if (o is String) {
            o as String
        } else {
            o.toString()
        }
        s = s.trim { it <= ' ' }
        if (isEmpty(s)) {
            return value
        }

        try {
            value = s.toFloat()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            try {
                value = s.toDouble().toFloat()
            } catch (e1: NumberFormatException) {
                e1.printStackTrace()
            }
        }

        return value
    }

    fun toFloat(o: Any?): Float? {
        return toFloat(o, null)
    }

    fun toFloat(o: Any?, defaultValue: Float?): Float? {
        var value = defaultValue
        if (o == null) {
            return value
        }

        if (o is Float) {
            return o
        }

        if (o is Number) {
            return o.toFloat()
        }
        var s = if (o is String) {
            o as String
        } else {
            o.toString()
        }
        s = s.trim { it <= ' ' }

        if (isEmpty(s)) {
            return value
        }

        try {
            value = s.toFloat()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            try {
                value = s.toDouble().toFloat()
            } catch (e1: NumberFormatException) {
                e1.printStackTrace()
            }
        }

        return value
    }


    // ==================== Double =======================//
    fun toDoubleValue(value: Any?): Double {
        return toDoubleValue(value, 0.0)
    }

    fun toDoubleValue(o: Any?, defaultValue: Double): Double {
        var value = defaultValue
        if (o == null) {
            return value
        }

        if (o is Number) {
            return o.toDouble()
        }
        var s = if (o is String) {
            o as String
        } else {
            o.toString()
        }
        s = s.trim { it <= ' ' }
        if (isEmpty(s)) {
            return value
        }

        try {
            value = s.toDouble()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }

        return value
    }

    fun toDouble(o: Any?): Double? {
        return toDouble(o, null)
    }

    fun toDouble(o: Any?, defaultValue: Double?): Double? {
        var value = defaultValue
        if (o == null) {
            return value
        }

        if (o is Double) {
            return o
        }

        if (o is Number) {
            return o.toDouble()
        }
        var s = if (o is String) {
            o as String
        } else {
            o.toString()
        }
        s = s.trim { it <= ' ' }

        if (isEmpty(s)) {
            return value
        }

        try {
            value = s.toDouble()
        } catch (e1: NumberFormatException) {
            e1.printStackTrace()
        }

        return value
    }
}