package com.github.xingray.kotlinbase.util.`object`

import java.math.BigDecimal
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.function.Function

class ObjectConverters {
    fun bigDecimalConverter(): Function<Any, BigDecimal> {
        return Function { o: Any -> BigDecimal(o.toString()) }
    }

    fun dateConverter(pattern: String?): Function<Any, Date> {
        return Function { o: Any ->
            val simpleDateFormat = SimpleDateFormat(pattern)
            try {
                return@Function simpleDateFormat.parse(o.toString())
            } catch (e: ParseException) {
                throw RuntimeException(e)
            }
        }
    }
}