package com.github.xingray.kotlinbase.config

import com.github.xingray.kotlinbase.util.ReflectUtil
import java.lang.reflect.Field

class ObjectConfig<T>(private val target: Any, private val configClass: Class<T>) {
    private val controllerViewFields: Map<String, Field>
    private val fieldConverterMap: ClassMap<FieldConverter<Any,Any>>

    init {
        this.fieldConverterMap = ClassMap()

        controllerViewFields = Util.getConfigFieldMap(target)
    }

    fun <V, S> addViewState(viewCls: Class<V>?, fieldConverter: FieldConverter<V, S>) {
        fieldConverterMap.put(viewCls!!, fieldConverter as FieldConverter<Any, Any>)
    }

    val config: T?
        get() {
            var config: T? = null
            try {
                config = configClass.getConstructor().newInstance()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            populateConfig(config)
            return config
        }

    fun populateConfig(config: T?) {
        if (config == null) {
            return
        }

        val declaredFields: Array<Field> = config.javaClass.declaredFields
        val configPropertyNames: MutableSet<String> = HashSet()
        for (field in declaredFields) {
            configPropertyNames.add(field.name)
        }

        configPropertyNames.retainAll(controllerViewFields.keys)
        for (name in configPropertyNames) {
            val field = controllerViewFields[name]
            try {
                val viewField = field!![target]
                val fieldConverter: FieldConverter<Any,Any>? = fieldConverterMap.getByClassObject(viewField)
                var value: Any? = null
                value = if (fieldConverter != null) {
                    fieldConverter.getConfig(viewField)
                } else {
                    viewField
                }
                ReflectUtil.set(config, name, value)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }

    fun populateView(config: T?) {
        if (config == null) {
            return
        }

        val declaredFields: Array<Field> = config.javaClass.declaredFields
        val configPropertyNames: MutableSet<String> = HashSet()
        for (field in declaredFields) {
            configPropertyNames.add(field.name)
        }

        configPropertyNames.retainAll(controllerViewFields.keys)
        for (name in configPropertyNames) {
            val field = controllerViewFields[name]
            try {
                val viewField = field!![target]
                val value: Any = ReflectUtil.get(config, name)!!
                val fieldConverter: FieldConverter<Any, Any>? = fieldConverterMap.getByClassObject(viewField)
                if (fieldConverter != null) {
                    fieldConverter.restoreConfig(viewField, value)
                } else {
                    field[target] = value
                }
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }
}