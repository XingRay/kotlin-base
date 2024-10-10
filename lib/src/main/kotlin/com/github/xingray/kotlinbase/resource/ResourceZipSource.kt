package com.github.xingray.coinfarmer.android.resource

import com.github.xingray.kotlinbase.zip.ZipSource
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class ResourceZipSource(val zipName: String, val resource: Resource) : ZipSource {
    override fun write(zipOutputStream: ZipOutputStream) {
        val entry = ZipEntry(zipName)
        zipOutputStream.putNextEntry(entry)
        resource.use { inputStream ->
            inputStream.copyTo(zipOutputStream)
        }
        zipOutputStream.closeEntry()
    }
}