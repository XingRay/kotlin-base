package com.github.xingray.kotlinbase.util

import java.io.File
import java.util.jar.JarFile

object ReflectionUtil {

    @JvmStatic
    private val TAG = ReflectionUtil::class.java.simpleName

    fun findClassesInPackage(packageName: String): List<Class<*>> {
        return findClassNamesInPackage(packageName).mapNotNull {
            try {
                Class.forName(it)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
                null
            }
        }
    }

    fun findClassNamesInPackage(packageName: String): List<String> {
        val packageDirName = packageName.replace('.', '/')
        val classLoader = Thread.currentThread().contextClassLoader

        return classLoader.getResources(packageDirName).asSequence().flatMap { resource ->
            return@flatMap if (resource.protocol.equals("file")) {
                val directory = File(resource.file)
                if (directory.exists()) {
                    directory.walkTopDown()
                        .filter { it.name.endsWith(".class") }
                        .map { packageName + it.absolutePath.removeSurrounding(directory.absolutePath, ".class").replace("\\", ".") }
                } else {
                    emptySequence()
                }
            } else if (resource.protocol.equals("jar")) {
                val jarPath = resource.file.substring(5, resource.file.length - packageDirName.length - 2)
                val jarFile = JarFile(jarPath)
                jarFile.entries().asSequence()
                    .filter { it.name.startsWith(packageDirName) && it.name.endsWith(".class") }
                    .map { it.name.removeSuffix(".class").replace('/', '.') }
            } else {
                emptySequence()
            }
        }.toList()
    }
}