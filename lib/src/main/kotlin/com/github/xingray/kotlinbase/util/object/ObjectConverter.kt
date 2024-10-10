package com.github.xingray.kotlinbase.util.`object`

import com.github.xingray.kotlinbase.util.NumberUtil
import com.github.xingray.kotlinbase.util.StringUtil
import java.util.*
import java.util.function.Function

class ObjectConverter {
    private val converters: MutableMap<Class<*>?, Function<Any, Any>> = HashMap()

    fun addDefaultConverters(): ObjectConverter {
        addConverter<String>(String::class.java, StringUtil::toString)
        addConverter<Long>(Long::class.java, NumberUtil::toLong)
        addConverter<Long>(Long::class.javaPrimitiveType!!, NumberUtil::toLongValue)
        addConverter<Int>(Int::class.java, NumberUtil::toInteger)
        addConverter<Int>(Int::class.javaPrimitiveType!!, NumberUtil::toIntValue)
        addConverter<Double>(Double::class.java, NumberUtil::toDouble)
        addConverter<Double>(Double::class.javaPrimitiveType!!, NumberUtil::toDoubleValue)
        addConverter<Float>(Float::class.java, NumberUtil::toFloat)
        addConverter<Float>(Float::class.javaPrimitiveType!!, NumberUtil::toFloatValue)
        addConverter<Boolean>(Boolean::class.java, NumberUtil::toBoolean)
        addConverter<Boolean>(Boolean::class.javaPrimitiveType!!, NumberUtil::toBooleanValue)
        addConverter<Byte>(Byte::class.java, NumberUtil::toByte)
        addConverter<Byte>(Byte::class.javaPrimitiveType!!, NumberUtil::toByteValue)
        addConverter<Short>(Short::class.java, NumberUtil::toShort)
        addConverter<Short>(Short::class.javaPrimitiveType!!, NumberUtil::toShortValue)
        addConverter<Char>(Char::class.java, NumberUtil::toCharacter)
        addConverter<Char>(Char::class.javaPrimitiveType!!, NumberUtil::toCharValue)

        return this
    }

    fun <T> addConverter(cls: Class<T>, function: Function<Any, T?>): ObjectConverter {
        converters[cls] = function as Function<Any, Any>
        return this
    }

    fun <T> convert(src: Any?, targetType: Class<T>): T? {
        if (src == null) {
            return null
        }

        if (targetType.isInstance(src) || targetType.isAssignableFrom(src.javaClass)) {
            return src as T
        }

        val function = converters[targetType] ?: return null
        return function.apply(src) as T
    }

    fun stringArrayToTypeArray(src: Array<String>?, elementType: Class<*>): Any? {
        if (src == null) {
            return null
        }

        if (elementType == String::class.java) {
            return src.copyOf(src.size)
        }

        val targetArray = java.lang.reflect.Array.newInstance(elementType, src.size)
        val converter = converters[elementType] ?: throw IllegalArgumentException("unsupported type:${elementType.name}, invoke  ObjectConverter#addConverter() to add converter")

        var i = 0
        val arrayLength = src.size
        while (i < arrayLength) {
            java.lang.reflect.Array.set(targetArray, i, converter.apply(src[i]))
            i++
        }
        return targetArray
    }

    fun <T> arrayToTypeArray(array: Array<T>?, elementType: Class<*>): Any? {
        if (array == null) {
            return null
        }

        if (elementType == array.javaClass.componentType) {
            return array.copyOf(array.size)
        }

        val converter = converters[elementType] ?: throw IllegalArgumentException("unsupported type:${elementType.name}, invoke  ObjectConverter#addConverter() to add converter")

        val targetArray = java.lang.reflect.Array.newInstance(elementType, array.size)
        var i = 0
        val arrayLength = array.size
        while (i < arrayLength) {
            java.lang.reflect.Array.set(targetArray, i, converter.apply(array[i] as Any))
            i++
        }
        return targetArray
    }

    fun <R> arrayToTypeList(array: Any?, elementType: Class<R>): List<R>? {
        if (array == null) {
            return null
        }
        val length = java.lang.reflect.Array.getLength(array)
        if (length == 0) {
            return emptyList()
        }

        if (elementType == array.javaClass.componentType) {
            return Arrays.asList(array) as List<R>
        }

        val converter = converters[elementType] ?: throw IllegalArgumentException("unsupported type:${elementType.name}, invoke  ObjectConverter#addConverter() to add converter")

        val list: ArrayList<R> = ArrayList<R>(length)
        var i = 0
        val arrayLength = length
        while (i < arrayLength) {
            val value = java.lang.reflect.Array.get(array, i)
            val element = converter.apply(value as Any) as R
            list.add(element)
            i++
        }
        return list
    }
}