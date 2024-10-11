package com.github.xingray.kotlinbase.resource

import java.io.InputStream

class JarInnerResource(val path: String) : Resource {

    private val TAG = JarInnerResource::class.java.simpleName

    override fun <R> use(consumer: (InputStream) -> R): R? {
        val inputStream = JarInnerResource::class.java.classLoader.getResourceAsStream(path) ?: let {
            return null
        }
        return consumer.invoke(inputStream)
    }
}