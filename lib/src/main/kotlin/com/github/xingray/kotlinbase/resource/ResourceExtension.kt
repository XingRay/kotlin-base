package com.github.xingray.kotlinbase.resource

import com.github.xingray.kotlinbase.ext.io.ensureFileExists
import com.github.xingray.kotlinbase.graphic.Size
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileOutputStream
import javax.imageio.ImageIO


private val TAG = "ResourceExtension"

fun Resource.readBufferedImage(): BufferedImage? {
    return use { ImageIO.read(it) }
}

fun Resource.readBufferedImageAsSize(width: Int, height: Int): BufferedImage? {
    return use { ImageIO.read(it) }
}

fun Resource.readImageSize(): Size? {
//    val exifInterface = this.use { inputStream ->
//        ExifInterface(inputStream)
//    } ?: let {
//        Log.e(TAG, "readBitmap: can not open inputStream, InputStreamProvider:${this}")
//        return null
//    }
//
//    val rotation = exifInterface.rotationDegrees
//    val orientation: Int = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
//    val mirror = orientation == ExifInterface.ORIENTATION_TRANSVERSE || orientation == ExifInterface.ORIENTATION_FLIP_VERTICAL
//            || orientation == ExifInterface.ORIENTATION_TRANSPOSE || orientation == ExifInterface.ORIENTATION_FLIP_HORIZONTAL
//    val exifWidth = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 0)
//    val exifHeight = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 0)
//    Log.d(TAG, "InputStream.readBitmap: exifInterface: rotation:${rotation}, mirror:${mirror}, exifWidth:${exifWidth}, exifHeight:${exifHeight}")
//
//    val size = if (exifWidth <= 0 || exifHeight <= 0) {
//        this.use { inputStream -> inputStream.readImageSize() } ?: let {
//            Log.e(TAG, "readBitmap: can not open inputStream, InputStreamProvider:${this}")
//            return null
//        }
//    } else {
//        Size(exifWidth, exifHeight)
//    }
//
//    return size.rotate(rotation)
    // TODO:
    return Size(0,0)
}

fun Resource.copyTo(file: File) {
    file.ensureFileExists()
    if (this is FileResource && this.file.absolutePath == file.absolutePath) {
        return
    }

    use { inputStream ->
        FileOutputStream(file).use { outputStream ->
            inputStream.copyTo(outputStream)
        }
    }
}

fun File.resoleAsFileResource(fileName: String?): FileResource? {
    if (fileName.isNullOrEmpty()) {
        return null
    }
    val dir = this
    if (!dir.exists() || dir.isFile) {
        return null
    }

    val file = dir.resolve(fileName)
    if (!file.exists() || !file.isFile) {
        return null
    }
    return FileResource(file)
}