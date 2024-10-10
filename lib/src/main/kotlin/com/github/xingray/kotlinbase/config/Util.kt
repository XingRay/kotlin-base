package com.github.xingray.kotlinbase.config

import com.github.xingray.kotlinbase.config.annotations.ConfigKey
import java.lang.reflect.Field

object Util {

    fun getConfigFieldMap(target: Any): Map<String, Field> {
        val targetConfigFields: MutableMap<String, Field> = HashMap()
        val fields = target.javaClass.fields
        for (field in fields) {
            val configKeyAnnotation: ConfigKey = field.getAnnotation(ConfigKey::class.java) ?: continue
            val configKey: String = configKeyAnnotation.value
            targetConfigFields[configKey] = field
        }

        return targetConfigFields
    }
}
