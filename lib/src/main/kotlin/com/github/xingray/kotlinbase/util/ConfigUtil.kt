package com.github.xingray.kotlinbase.util

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

object ConfigUtil {
    fun loadConfig(path: String?): Map<String, String>? {
        val properties = loadProperties(path)
        return propertiesToStringMap(properties)
    }

    fun saveConfig(path: String?, config: Map<String?, String?>?) {
        val properties = mapToProperties(config)
        saveProperties(path, properties)
    }

    fun loadProperties(path: String?): Properties {
        val properties = Properties()
        if (!FileUtil.isFileExist(path)) {
            return properties
        }

        try {
            FileInputStream(path).use { fileInputStream ->
                properties.load(fileInputStream)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return properties
    }

    fun saveProperties(path: String?, properties: Properties?) {
        if (properties == null) {
            return
        }
        FileUtil.createFileIfNotExist(path)

        try {
            FileOutputStream(path).use { fileOutputStream ->
                properties.store(fileOutputStream, null)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getProperty(path: String?, key: String?): String {
        return loadProperties(path).getProperty(key)
    }

    fun putProperty(path: String?, key: String?, value: String?) {
        val properties = loadProperties(path)
        properties[key] = value
        saveProperties(path, properties)
    }

    fun <T> readFromProperties(path: String, cls: Class<T>): T? {
        val properties = loadProperties(path)
        val map = propertiesToMap(properties)
        return ObjectUtil.mapToObject(map, cls)
    }

    fun <T> writeToProperties(path: String, t: T) {
        val map: Map<String, Any>? = ObjectUtil.objectToMap(t)
        val properties = mapToProperties(map)
        saveProperties(path, properties)
    }

    fun <T> populateFromProperties(path: String, t: T?): T? {
        if (t == null) {
            return null
        }
        val properties = loadProperties(path)
        val map = propertiesToMap(properties)
        return ObjectUtil.populateObject(t, map)
    }

    fun propertiesToMap(properties: Properties?): Map<String, Any>? {
        if (properties == null || properties.isEmpty) {
            return null
        }

        val map: MutableMap<String, Any> = HashMap<String, Any>(properties.size)
        for ((key, value) in properties) {
            map[key.toString()] = value
        }
        return map
    }

    fun propertiesToStringMap(properties: Properties?): Map<String, String>? {
        if (properties == null || properties.isEmpty) {
            return null
        }

        val map: MutableMap<String, String> = HashMap<String, String>(properties.size)
        for (entry in properties.entries) {
            var key = entry.key
            if (key == null) {
                key = ""
            }
            var value = entry.value
            if (value == null) {
                value = ""
            }
            map[key.toString()] = value.toString()
        }
        return map
    }

    fun mapToProperties(map: Map<*, *>?): Properties? {
        if (map == null || map.isEmpty()) {
            return null
        }

        val properties = Properties(map.size)
        for ((key1, value1) in map) {
            val key = key1!!
            val value = value1!!
            properties[StringUtil.toString(key)] = StringUtil.toString(value, "")
        }
        return properties
    }
}