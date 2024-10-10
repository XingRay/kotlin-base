package com.github.xingray.kotlinbase.util

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

object IOUtil {
    @Throws(IOException::class)
    fun inputStreamToString(inputStream: InputStream, charset: String?): String {
        val result = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var length: Int
        while ((inputStream.read(buffer).also { length = it }) != -1) {
            result.write(buffer, 0, length)
        }
        return result.toString(charset)
    }

    @Throws(IOException::class)
    fun inputStreamToString(inputStream: InputStream): String {
        return inputStreamToString(inputStream, "UTF-8")
    }
}