package com.github.xingray.kotlinbase.config

class ClassMap<T> {
    private val map = HashMap<Class<*>, T>()

    fun put(cls: Class<*>, t: T): T? {
        return map.put(cls, t)
    }

    fun getByClassObject(viewField: Any): T? {
        var t = map[viewField.javaClass]
        if (t == null) {
            for ((cls, value) in map) {
                if (cls.isInstance(viewField)) {
                    t = value
                    if (t != null) {
                        map[cls] = t
                    }
                    break
                }
            }
        }
        return t
    }
}