package com.github.xingray.kotlinbase.util

import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

object ReflectUtil {
    /**
     * 根据属性，获取get方法
     *
     * @param fieldName 属性名
     */
    fun getGetter(cls: Class<*>, fieldName: String): Method? {
        var field: Field? = null
        try {
            field = cls.getDeclaredField(fieldName)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        if (field == null) {
            return null
        }

        val type = field.type
        val methodName = getGetterName(fieldName, type)

        try {
            return cls.getMethod(methodName)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
        return null
    }

    fun getGetterName(fieldName: String, type: Class<*>): String {
        val methodName = if (type == Boolean::class.javaPrimitiveType) {
            if (isStartWithIsField(fieldName)) {
                fieldName
            } else {
                "is" + StringUtil.captain(fieldName)
            }
        } else if (type == Boolean::class.java) {
            if (isStartWithIsField(fieldName)) {
                "get" + StringUtil.captain(fieldName.substring(2))
            } else {
                "get" + StringUtil.captain(fieldName)
            }
        } else {
            "get" + StringUtil.captain(fieldName)
        }

        return methodName
    }

    fun isStartWithIsField(fieldName: String): Boolean {
        return fieldName.startsWith("is") && fieldName.length > 2 && Character.isUpperCase(fieldName[2])
    }

    /**
     * 根据属性，获取set方法
     */
    fun getSetter(cls: Class<*>, fieldName: String): Method? {
        var field: Field? = null
        try {
            field = cls.getDeclaredField(fieldName)
        } catch (ignored: NoSuchFieldException) {
        }
        if (field == null) {
            return null
        }

        val type = field.type
        val methodName = getSetterName(fieldName, type)

        try {
            return cls.getMethod(methodName, type)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
        return null
    }

    fun getSetterName(fieldName: String, type: Class<*>): String {
        val methodName = if (type == Boolean::class.javaPrimitiveType) {
            if (isStartWithIsField(fieldName)) {
                "set" + StringUtil.captain(fieldName.substring(2))
            } else {
                "set" + StringUtil.captain(fieldName)
            }
        } else if (type == Boolean::class.java) {
            if (isStartWithIsField(fieldName)) {
                "set" + StringUtil.captain(fieldName.substring(2))
            } else {
                "set" + StringUtil.captain(fieldName)
            }
        } else {
            "set" + StringUtil.captain(fieldName)
        }
        return methodName
    }


    fun get(o: Any, fieldName: String): Any? {
        return get(o, fieldName, null)
    }

    fun get(o: Any, fieldName: String, getterCache: MutableMap<String?, Method?>?): Any? {
        var getter: Method? = null
        if (getterCache != null && !getterCache.isEmpty()) {
            getter = getterCache[fieldName]
        }
        if (getter == null) {
            getter = getGetter(o.javaClass, fieldName)
            if (getter != null && getterCache != null) {
                getterCache[fieldName] = getter
            }
        }
        if (getter == null) {
            return null
        }
        try {
            return getter.invoke(o)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return null
    }

    fun set(o: Any, fieldName: String, value: Any?) {
        set(o, fieldName, value, null)
    }

    fun set(o: Any, fieldName: String, value: Any?, setterCache: MutableMap<String?, Method?>?) {
        var setter: Method? = null
        if (setterCache != null && !setterCache.isEmpty()) {
            setter = setterCache[fieldName]
        }
        if (setter == null) {
            setter = getSetter(o.javaClass, fieldName)
            if (setter != null && setterCache != null) {
                setterCache[fieldName] = setter
            }
        }
        if (setter == null) {
            return
        }
        try {
            val paramClass = setter.parameterTypes[0]
            setter.invoke(o, ObjectUtil.ensureMatchesType(value, paramClass))
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    }
}