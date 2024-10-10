package com.github.xingray.kotlinbase.config

import com.github.xingray.kotlinbase.config.annotations.ConfigType
import com.github.xingray.kotlinbase.config.annotations.Path
import com.github.xingray.kotlinbase.config.annotations.RealPath
import com.github.xingray.kotlinbase.config.annotations.UsePackageName
import com.github.xingray.kotlinbase.util.ConfigUtil
import com.github.xingray.kotlinbase.util.FileUtil

class Config(private val rootPath: String? = null) {
    private val configs: MutableMap<Class<*>, Any?> = HashMap()
    private val pathCache: MutableMap<Class<*>, String?> = HashMap()
    private var jsonEngine: JsonEngine? = null

    fun setJsonEngine(jsonEngine: JsonEngine?) {
        this.jsonEngine = jsonEngine
    }

    fun initForClasses(vararg classes: Class<*>) {
        for (c in classes) {
            initForClass(c)
        }
    }

    fun initForClasses(classes: Iterable<Class<*>>) {
        for (c in classes) {
            initForClass(c)
        }
    }

    fun <T> initForClass(cls: Class<T>) {
        try {
            val o = cls.getDeclaredConstructor().newInstance()
            load(o, cls)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun init(vararg objects: Any) {
        for (o in objects) {
            val cls = o.javaClass
            load(o, cls)
        }
    }

    fun init(objects: Iterable<Any>) {
        for (o in objects) {
            val cls = o.javaClass
            load(o, cls)
        }
    }

    fun <T> init(config: T) {
        val cls = config!!::class.java as Class<T>
        load(config, cls)
    }

    fun <T> init(config: T, path: String) {
        val cls = config!!::class.java as Class<T>
        load(config, cls, path)
    }


    private fun <T> load(config: T, cls: Class<T>): T? {
        val configType = getConfigType(cls)
        return if (configType == ConfigType.PROPERTIES) {
            loadProperties<T>(config, cls)
        } else if (configType == ConfigType.JSON) {
            loadJson<T>(cls)
        } else {
            null
        }
    }

    private fun <T> load(config: T, cls: Class<T>, path: String): T? {
        val configType = getConfigType(cls)
        return if (configType == ConfigType.PROPERTIES) {
            loadProperties<T>(config, cls, path)
        } else if (configType == ConfigType.JSON) {
            loadJson<T>(cls, path)
        } else {
            null
        }
    }

    private fun <T> loadProperties(config: T, cls: Class<T>, path: String): T? {
        var config: T? = config
        if (config == null) {
            try {
                config = cls.getDeclaredConstructor().newInstance()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        config = ConfigUtil.populateFromProperties(path, config)
        configs[cls] = config
        return config
    }

    private fun <T> loadJson(cls: Class<T>, path: String): T? {
        val s: String = FileUtil.readFile(path) ?: ""
        val config: T? = jsonEngine?.fromJson(s, cls)
        configs[cls] = config
        return config
    }


    private fun <T> loadProperties(config: T, cls: Class<T>): T? {
        var path = pathCache[cls]
        if (path == null) {
            path = makePath<Any>(cls)
            pathCache[cls] = path
        }
        return loadProperties(config, cls, path)
    }

    private fun <T> loadJson(cls: Class<T>): T? {
        var path = pathCache[cls]
        if (path == null) {
            path = makePath<Any>(cls)
            pathCache[cls] = path
        }
        return loadJson(cls, path)
    }


    fun <T> get(cls: Class<T?>): T? {
        var t = configs[cls] as T?
        if (t == null) {
            t = load(null, cls)
        }
        return t
    }

    fun <T> get(cls: Class<T?>, path: String): T? {
        var t = configs[cls] as T?
        if (t == null) {
            t = load(null, cls, path)
        }
        return t
    }


    inline fun <reified T> save(config: T) {
        val cls: Class<T> = T::class.java
        innerSave(config, cls)
    }

    fun <T> innerSave(config: T, cls: Class<T>) {
        var path = pathCache[cls]
        if (path == null) {
            path = makePath<Any>(cls)
            pathCache[cls] = path
        }

        innerSave(config, cls, path)
    }

    inline fun <reified T> save(config: T, path: String) {
        val cls: Class<T> = T::class.java
        innerSave(config, cls, path)
    }

    fun <T> innerSave(config: T, cls: Class<T>, path: String) {
        val configType = getConfigType(cls)
        if (configType == ConfigType.JSON) {
            FileUtil.writeFile(path, toJson(config))
        } else if (configType == ConfigType.PROPERTIES) {
            ConfigUtil.writeToProperties(path, config)
        }
        configs[cls] = config
    }

    fun <T> toJson(t: T): String? {
        return jsonEngine?.toJson(t)
    }

    fun <T> makePath(cls: Class<*>): String {
        val realPathAnnotation: RealPath = cls.getAnnotation(RealPath::class.java)
        if (realPathAnnotation != null) {
            return realPathAnnotation.value
        }

        val pathAnnotation: Path = cls.getAnnotation(Path::class.java)
        if (pathAnnotation != null) {
            return rootPath + pathAnnotation.value
        }

        var path: String
        val usePackageNameAnnotation: UsePackageName = cls.getAnnotation(UsePackageName::class.java)
        path = if (usePackageNameAnnotation == null) {
            rootPath + cls.name
        } else {
            rootPath + cls.canonicalName.replace(".", "_")
        }

        val value = getConfigType(cls)
        if (value == ConfigType.PROPERTIES) {
            path += ".properties"
        } else if (value == ConfigType.JSON) {
            path += ".json"
        }

        return path
    }

    fun getConfigType(cls: Class<*>): Int {
        val configTypeAnnotation: ConfigType = cls.getAnnotation(ConfigType::class.java)
        if (configTypeAnnotation != null) {
            return configTypeAnnotation.value
        }
        return ConfigType.PROPERTIES
    }
}