package com.github.xingray.kotlinbase.util

import com.github.xingray.kotlinbase.interfaces.function.serializable.SFunction
import java.lang.invoke.SerializedLambda
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object ObjectUtil {
    fun <T> hasValue(obj: T?): Boolean {
        return obj != null
    }

    fun <T> mapToObject(map: Map<String, Any>?, cls: Class<T>): T? {
        if (map == null || map.isEmpty()) {
            return null
        }

        var instance: T? = null
        try {
            instance = cls.getConstructor().newInstance()
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }
        if (instance == null) {
            return null
        }
        for ((key, value) in map) {
            if (key != null) {
                ReflectUtil.set(instance, key, value)
            }
        }

        return instance
    }

    fun <T> objectToMap(t: T?): Map<String, Any>? {
        if (t == null) {
            return null
        }
        val fields: Array<Field> = t.javaClass.declaredFields
        val size = fields.size
        if (size == 0) {
            return null
        }
        val map: MutableMap<String, Any> = HashMap(size)
        for (field in fields) {
            val fieldName = field.name
            map[fieldName] = ReflectUtil.get(t, fieldName) as Any
        }

        return map
    }

    /**
     * 填充对象
     */
    fun <T> populateObject(t: T?, map: Map<String, Any>?): T? {
        if (map == null || map.isEmpty()) {
            return t
        }
        if (t == null) {
            return t
        }

        for ((key, value) in map) {
            if (key != null) {
                ReflectUtil.set(t, key, value)
            }
        }

        return t
    }

    fun <T, M : MutableMap<String?, Any?>> populateMap(t: T?, map: M): M {
        if (t == null) {
            return map
        }
        val fields: Array<Field> = t.javaClass.declaredFields
        if (fields.size == 0) {
            return map
        }

        for (field in fields) {
            val fieldName = field.name
            map.put(fieldName, ReflectUtil.get(t, fieldName))
        }

        return map
    }


    fun ensureMatchesType(value: Any?, type: Class<*>): Any? {
        if (value == null) {
            return null
        }

        if (type.isInstance(value) || type.isAssignableFrom(value.javaClass)) {
            return value
        }

        if (type == String::class.java) {
            return StringUtil.toString(value)
        } else if (type == Long::class.java) {
            return NumberUtil.toLong(value)
        } else if (type == Long::class.javaPrimitiveType) {
            return NumberUtil.toLongValue(value)
        } else if (type == Int::class.java) {
            return NumberUtil.toInteger(value)
        } else if (type == Int::class.javaPrimitiveType) {
            return NumberUtil.toIntValue(value)
        } else if (type == Double::class.java) {
            return NumberUtil.toDouble(value)
        } else if (type == Double::class.javaPrimitiveType) {
            return NumberUtil.toDoubleValue(value)
        } else if (type == Float::class.java) {
            return NumberUtil.toFloat(value)
        } else if (type == Float::class.javaPrimitiveType) {
            return NumberUtil.toFloatValue(value)
        } else if (type == Boolean::class.java) {
            return NumberUtil.toBoolean(value)
        } else if (type == Boolean::class.javaPrimitiveType) {
            return NumberUtil.toBooleanValue(value)
        } else if (type == Byte::class.java) {
            return NumberUtil.toByte(value)
        } else if (type == Byte::class.javaPrimitiveType) {
            return NumberUtil.toByteValue(value)
        } else if (type == Short::class.java) {
            return NumberUtil.toShort(value)
        } else if (type == Short::class.javaPrimitiveType) {
            return NumberUtil.toShortValue(value)
        } else if (type == Char::class.java) {
            return NumberUtil.toCharacter(value)
        } else if (type == Char::class.javaPrimitiveType) {
            return NumberUtil.toCharValue(value)
        }

        return null
    }

    fun stringArrayToTypeArray(array: Array<String?>?, elementType: Class<*>): Any? {
        if (array == null) {
            return null
        }

        if (elementType == String::class.java) {
            return array
        }

        val targetArray = java.lang.reflect.Array.newInstance(elementType, array.size)
        if (elementType == Long::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toLong(array[i]))
                i++
            }
        } else if (elementType == Long::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toLongValue(array[i]))
                i++
            }
        } else if (elementType == Int::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toInteger(array[i]))
                i++
            }
        } else if (elementType == Int::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toIntValue(array[i]))
                i++
            }
        } else if (elementType == Double::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toDouble(array[i]))
                i++
            }
        } else if (elementType == Double::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toDoubleValue(array[i]))
                i++
            }
        } else if (elementType == Float::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toFloat(array[i]))
                i++
            }
        } else if (elementType == Float::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toFloatValue(array[i]))
                i++
            }
        } else if (elementType == Boolean::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toBoolean(array[i]))
                i++
            }
        } else if (elementType == Boolean::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toBooleanValue(array[i]))
                i++
            }
        } else if (elementType == Byte::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toByte(array[i]))
                i++
            }
        } else if (elementType == Byte::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toByteValue(array[i]))
                i++
            }
        } else if (elementType == Short::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toShort(array[i]))
                i++
            }
        } else if (elementType == Short::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toShortValue(array[i]))
                i++
            }
        } else if (elementType == Char::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toCharacter(array[i]))
                i++
            }
        } else if (elementType == Char::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toCharValue(array[i]))
                i++
            }
        }

        return targetArray
    }

    fun <T> arrayToTypeArray(array: Array<T>?, elementType: Class<*>): Any? {
        if (array == null) {
            return null
        }

        if (elementType == array.javaClass.componentType) {
            return array
        }

        val targetArray = java.lang.reflect.Array.newInstance(elementType, array.size)
        if (elementType == Long::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toLong(array[i]))
                i++
            }
        } else if (elementType == Long::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toLongValue(array[i]))
                i++
            }
        } else if (elementType == Int::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toInteger(array[i]))
                i++
            }
        } else if (elementType == Int::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toIntValue(array[i]))
                i++
            }
        } else if (elementType == Double::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toDouble(array[i]))
                i++
            }
        } else if (elementType == Double::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toDoubleValue(array[i]))
                i++
            }
        } else if (elementType == Float::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toFloat(array[i]))
                i++
            }
        } else if (elementType == Float::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toFloatValue(array[i]))
                i++
            }
        } else if (elementType == Boolean::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toBoolean(array[i]))
                i++
            }
        } else if (elementType == Boolean::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toBooleanValue(array[i]))
                i++
            }
        } else if (elementType == Byte::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toByte(array[i]))
                i++
            }
        } else if (elementType == Byte::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toByteValue(array[i]))
                i++
            }
        } else if (elementType == Short::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toShort(array[i]))
                i++
            }
        } else if (elementType == Short::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toShortValue(array[i]))
                i++
            }
        } else if (elementType == Char::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toCharacter(array[i]))
                i++
            }
        } else if (elementType == Char::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                java.lang.reflect.Array.set(targetArray, i, NumberUtil.toCharValue(array[i]))
                i++
            }
        }

        return targetArray
    }

    fun <T, R> arrayToTypeList(array: Array<T>?, elementType: Class<R>): List<R>? {
        if (array == null) {
            return null
        }
        if (array.size == 0) {
            return emptyList()
        }

        if (elementType == array.javaClass.componentType) {
            return Arrays.asList(*array) as List<R>
        }

        val list = mutableListOf<R>()
        if (elementType == Long::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                list.add(NumberUtil.toLong(array[i]) as R)
                i++
            }
        } else if (elementType == Long::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                list.add(NumberUtil.toLongValue(array[i]) as R)
                i++
            }
        } else if (elementType == Int::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                list.add(NumberUtil.toInteger(array[i]) as R)
                i++
            }
        } else if (elementType == Int::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                list.add(NumberUtil.toIntValue(array[i]) as R)
                i++
            }
        } else if (elementType == Double::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                list.add(NumberUtil.toDouble(array[i]) as R)
                i++
            }
        } else if (elementType == Double::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                list.add(NumberUtil.toDoubleValue(array[i]) as R)
                i++
            }
        } else if (elementType == Float::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                list.add(NumberUtil.toFloat(array[i]) as R)
                i++
            }
        } else if (elementType == Float::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                list.add(NumberUtil.toFloatValue(array[i]) as R)
                i++
            }
        } else if (elementType == Boolean::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                list.add(NumberUtil.toBoolean(array[i]) as R)
                i++
            }
        } else if (elementType == Boolean::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                list.add(NumberUtil.toBooleanValue(array[i]) as R)
                i++
            }
        } else if (elementType == Byte::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                list.add(NumberUtil.toByte(array[i]) as R)
                i++
            }
        } else if (elementType == Byte::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                list.add(NumberUtil.toByteValue(array[i]) as R)
                i++
            }
        } else if (elementType == Short::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                list.add(NumberUtil.toShort(array[i]) as R)
                i++
            }
        } else if (elementType == Short::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                list.add(NumberUtil.toShortValue(array[i]) as R)
                i++
            }
        } else if (elementType == Char::class.java) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                list.add(NumberUtil.toCharacter(array[i]) as R)
                i++
            }
        } else if (elementType == Char::class.javaPrimitiveType) {
            var i = 0
            val arrayLength = array.size
            while (i < arrayLength) {
                list.add(NumberUtil.toCharValue(array[i]) as R)
                i++
            }
        }

        return list
    }


    @Throws(NoSuchMethodException::class, InstantiationException::class, IllegalAccessException::class, InvocationTargetException::class)
    fun setListValue(paramObject: Any?, field: Field, values: Array<String>, fieldType: Class<*>) {
        //list类型
        var list: List<Any>?
        list = if (fieldType == Array<String>::class.java) {
            Arrays.asList(*values)
        } else {
            arrayToTypeList(values, fieldType)
        }
        if (fieldType != MutableList::class.java) {
            val constructor = fieldType.getDeclaredConstructor(null)
            val newList = constructor.newInstance(null) as MutableList<Any>
            if (list != null) {
                newList.addAll(list)
            }
            list = newList
        }
        if (list != null) {
            field.isAccessible = true
            field[paramObject] = list
        }
    }

    @Throws(IllegalAccessException::class)
    fun setArrayValue(paramObject: Any?, field: Field, values: Array<String>, fieldType: Class<*>) {
        // 数组类型
        val array = if (fieldType == Array<String>::class.java) {
            values.copyOf(values.size)
        } else {
            arrayToTypeArray(values, fieldType.componentType)
        }
        if (array != null) {
            field.isAccessible = true
            field[paramObject] = array
        }
    }

    fun isAllNull(vararg objects: Any?): Boolean {
        for (o in objects) {
            if (o != null) {
                return false
            }
        }
        return true
    }

    fun isAtLeastOneIsNotNull(vararg objects: Any?): Boolean {
        for (o in objects) {
            if (o != null) {
                return true
            }
        }
        return false
    }

    fun isNoneNull(vararg objects: Any?): Boolean {
        for (o in objects) {
            if (o == null) {
                return false
            }
        }
        return true
    }

    fun isAtLeastOneIsNull(vararg objects: Any?): Boolean {
        for (o in objects) {
            if (o == null) {
                return true
            }
        }
        return false
    }

    fun <T> columnToString(column: SFunction<T, *>): String? {
        val writeReplaceMethod = getInheritableMethod((column as Any).javaClass, "writeReplace", null, Any::class.java) ?: return null

        try {
            val serializedLambda = writeReplaceMethod.invoke(column, null as Array<Any?>?) as SerializedLambda
            return methodToProperty(serializedLambda.implMethodName)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            return null
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
            return null
        }
    }

    private val columnCache: MutableMap<Class<*>, String?> = ConcurrentHashMap()

    fun <T> columnToStringCached(column: SFunction<T, *>): String? {
        val cachedName = columnCache[(column as Any).javaClass]
        if (cachedName != null) {
            return cachedName
        }
        val columnName = columnToString(column)
        columnCache[(column as Any).javaClass] = columnName
        return columnName
    }

    fun getInheritableMethod(cls: Class<*>, name: String?, argTypes: Array<Class<*>>?, returnType: Class<*>): Method? {
        var meth: Method? = null
        var defCl: Class<*>? = cls
        while (defCl != null) {
            try {
                if (argTypes == null) {
                    meth = defCl.getDeclaredMethod(name)
                } else {
                    meth = defCl.getDeclaredMethod(name, *argTypes)
                }
                break
            } catch (ex: NoSuchMethodException) {
                defCl = defCl.superclass
            }
        }

        if ((meth == null) || (meth.returnType != returnType)) {
            return null
        }
        meth.isAccessible = true
        val mods = meth.modifiers
        return if ((mods and (Modifier.STATIC or Modifier.ABSTRACT)) != 0) {
            null
        } else if ((mods and (Modifier.PUBLIC or Modifier.PROTECTED)) != 0) {
            meth
        } else if ((mods and Modifier.PRIVATE) != 0) {
            if ((cls == defCl)) meth else null
        } else {
            if (packageEquals(cls, defCl)) meth else null
        }
    }

    fun packageEquals(cl1: Class<*>, cl2: Class<*>?): Boolean {
        return cl1.classLoader === cl2!!.classLoader &&
                cl1.packageName === cl2!!.packageName
    }

    fun methodToProperty(name: String): String {
        var name = name
        name = if (name.startsWith("is")) {
            name.substring(2)
        } else if (name.startsWith("get") || name.startsWith("set")) {
            name.substring(3)
        } else {
            throw RuntimeException("Error parsing property name '$name'.  Didn't start with 'is', 'get' or 'set'.")
        }

        if (name.length == 1 || (name.length > 1 && !Character.isUpperCase(name[1]))) {
            name = name.substring(0, 1).lowercase() + name.substring(1)
        }

        return name
    }
}