package com.github.xingray.kotlinbase.config

import java.lang.reflect.Field

class MapConfig(private val target: Any) {
    private val targetConfigFields: Map<String, Field> = Util.getConfigFieldMap(target)

    private val fieldConverterMap: ClassMap<FieldConverter<Any, String>> = ClassMap()

    fun <V> addFieldConverter(viewCls: Class<V>, fieldConverter: FieldConverter<Any, String>) {
        fieldConverterMap.put(viewCls, fieldConverter)
    }

    val config: Map<String, String>
        get() {
            val config: MutableMap<String, String> = HashMap()
            getConfig(config)
            return config
        }

    fun getConfig(config: MutableMap<String, String>?) {
        if (config == null) {
            return
        }

        for ((name, field) in targetConfigFields) {
            var targetField: Any? = null
            try {
                targetField = field[target]
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
            if (targetField == null) {
                return
            }

            val fieldConverter: FieldConverter<Any, String>? = fieldConverterMap.getByClassObject(targetField)
            var value: Any? = null
            value = if (fieldConverter != null) {
                fieldConverter.getConfig(targetField)
            } else {
                targetField
            }
            config[name] = value.toString()
        }
    }

    fun updateTarget(config: Map<String, String>) {
        val configPropertyNames: MutableSet<String> = config.keys.toMutableSet()
        configPropertyNames.retainAll(targetConfigFields.keys)
        for (name in configPropertyNames) {
            val field = targetConfigFields[name]
            try {
                val viewField = field!![target]
                val value = config[name]!!
                val fieldConverter: FieldConverter<Any, String>? = fieldConverterMap.getByClassObject(viewField)
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
