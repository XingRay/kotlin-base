package com.github.xingray.kotlinbase.nativelibraryloader

import com.github.xingray.kotlinbase.ext.io.ensureDirExists
import java.io.File

class NativeLibraryLoader {
    companion object {

        private val TAG = NativeLibraryLoader::class.java.simpleName

        fun loadLibrary(saveDir: File, libPath: String) {
            val libFile = File(libPath)
            if (libFile.exists()) {
                System.load(libFile.getAbsolutePath());
                return
            }

            val targetLibFile = saveDir.resolve(libPath)
            val resource = NativeLibraryLoader::class.java.classLoader.getResourceAsStream(libPath) ?: let {
                throw IllegalArgumentException("can not read resource, libPath:${libPath}")
            }
            val dir = targetLibFile.parentFile
            dir.ensureDirExists()

            targetLibFile.outputStream().use { resource.copyTo(it) }
            System.load(targetLibFile.getAbsolutePath())
        }
    }
}