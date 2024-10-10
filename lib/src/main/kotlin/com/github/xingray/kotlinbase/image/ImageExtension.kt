package com.github.xingray.coinfarmer.image

import com.github.xingray.coinfarmer.android.resource.Resource
import com.github.xingray.kotlinbase.ext.io.ensureFileExists
import com.github.xingray.kotlinbase.ext.io.outputStream
import java.awt.AlphaComposite
import java.awt.image.*
import java.nio.file.Path
import javax.imageio.ImageIO


private val TAG = object : Any() {}::class.java.enclosingClass.simpleName ?: ""

fun BufferedImage.toRgbaInputImage(): Image? {
    val width = this.width
    val height = this.height

    if (colorModel is DirectColorModel) {
        val raster = this.raster
        val dataBuffer = raster.dataBuffer

        if (dataBuffer.dataType == DataBufferByte.TYPE_BYTE) {
            val pixelData = (dataBuffer as DataBufferByte).data
            return Image(width, height, pixelData)
        } else if (dataBuffer.dataType == DataBufferByte.TYPE_INT) {
            val pixelData = ByteArray(width * height * 4)
            val intPixelData = (dataBuffer as DataBufferInt).data
            var pixelIndex = 0
            for (pixel in intPixelData) {
                pixelData[pixelIndex++] = (pixel shr 16 and 0xFF).toByte() // 获取红色分量
                pixelData[pixelIndex++] = (pixel shr 8 and 0xFF).toByte()  // 获取绿色分量
                pixelData[pixelIndex++] = (pixel and 0xFF).toByte()         // 获取蓝色分量
                pixelData[pixelIndex++] = (pixel shr 24 and 0xFF).toByte() // 获取Alpha分量
            }
            return Image(width, height, pixelData)
        } else {
            throw UnsupportedOperationException("not supported yet, dataBuffer.dataType:${dataBuffer.dataType}")
        }
    } else if (colorModel is ComponentColorModel) {
        if (colorModel.numComponents == 3) {
            // RGB 颜色模型，通常是 3 字节表示一个像素
            val dataBuffer = this.raster.dataBuffer ?: let {
                return null
            }
            val dataType = dataBuffer.dataType
            if (dataType == DataBuffer.TYPE_BYTE) {
                val data = (dataBuffer as DataBufferByte).data
                return Image(width, height, data, format = ImageFormat.BGR).bgrToRgba()
            } else {
                throw UnsupportedOperationException("todo")
            }
        } else if (colorModel.numComponents == 4) {
            // ARGB 颜色模型，通常是 4 字节表示一个像素，第一个字节是 alpha 通道
            val dataBuffer = this.raster.dataBuffer ?: let {
                return null
            }
            val dataType = dataBuffer.dataType
            if (dataType == DataBuffer.TYPE_BYTE) {
                val data = (dataBuffer as DataBufferByte).data
                return Image(width, height, data, format = ImageFormat.ABGR).abgrToRgba()
            } else {
                throw UnsupportedOperationException("todo")
            }
        } else {
            throw UnsupportedOperationException("todo")
        }
    } else {
        // 其他类型，根据实际情况处理
        throw UnsupportedOperationException("todo")
    }
}

fun Image.setPixel(red: Int, green: Int, blue: Int, alpha: Int): Image {
    return setPixel(red.toByte(), green.toByte(), blue.toByte(), alpha.toByte())
}

fun Image.setPixel(red: Byte, green: Byte, blue: Byte, alpha: Byte): Image {
    val data = this.data ?: return this

    when (this.format) {
        ImageFormat.RGB -> {
            for (row in 0 until height) {
                for (column in 0 until width) {
                    data[(row * width + column) * 3 + 0] = red
                    data[(row * width + column) * 3 + 1] = green
                    data[(row * width + column) * 3 + 2] = blue
                }
            }
        }

        ImageFormat.RGBA -> {
            for (row in 0 until height) {
                for (column in 0 until width) {
                    data[(row * width + column) * 4 + 0] = red
                    data[(row * width + column) * 4 + 1] = green
                    data[(row * width + column) * 4 + 2] = blue
                    data[(row * width + column) * 4 + 3] = alpha
                }
            }
        }

        ImageFormat.ARGB -> {
            for (row in 0 until height) {
                for (column in 0 until width) {
                    data[(row * width + column) * 4 + 0] = alpha
                    data[(row * width + column) * 4 + 1] = red
                    data[(row * width + column) * 4 + 2] = green
                    data[(row * width + column) * 4 + 3] = blue
                }
            }
        }


        ImageFormat.BGR -> {
            for (row in 0 until height) {
                for (column in 0 until width) {
                    data[(row * width + column) * 3 + 0] = blue
                    data[(row * width + column) * 3 + 1] = green
                    data[(row * width + column) * 3 + 2] = red
                }
            }
        }

        ImageFormat.BGRA -> {
            for (row in 0 until height) {
                for (column in 0 until width) {
                    data[(row * width + column) * 4 + 0] = blue
                    data[(row * width + column) * 4 + 1] = green
                    data[(row * width + column) * 4 + 2] = red
                    data[(row * width + column) * 4 + 3] = alpha
                }
            }
        }

        ImageFormat.ABGR -> {
            for (row in 0 until height) {
                for (column in 0 until width) {
                    data[(row * width + column) * 4 + 0] = alpha
                    data[(row * width + column) * 4 + 1] = blue
                    data[(row * width + column) * 4 + 2] = green
                    data[(row * width + column) * 4 + 3] = red
                }
            }
        }

        else -> {
            throw IllegalArgumentException("this.format:${this.format}")
        }
    }
    return this
}

//fun BufferedImage.toInputImage(rotate: Int = 0, mirror: Boolean = false): InputImage {
//    val width = this.getWidth()
//    val height = this.getHeight()
//    //保存所有的像素的数组，图片宽×高
//    val pixels = this.raster.dataBuffer.
//
//    val bytes = ByteArray(width * height * 4)
//    for (i in pixels.indices) {
//        val argb = pixels[i].toLong()
//        val alpha: Byte = (argb and 0xff000000 shr 24).toByte() //取高两位
//        val red = (argb and 0x00ff0000 shr 16).toByte() //取高两位
//        val green = (argb and 0x0000ff00 shr 8).toByte() //取中两位
//        val blue = (argb and 0x000000ff).toByte() //取低两位
//        bytes[i * 4 + 0] = red
//        bytes[i * 4 + 1] = green
//        bytes[i * 4 + 2] = blue
//        bytes[i * 4 + 3] = alpha
//    }
//
//    return InputImage(width, height, bytes, rotate, mirror, ImageFormat.RGBA)
//}

fun Image.bgrToRgba(): Image {
    if (this.format != ImageFormat.BGR) {
        throw IllegalArgumentException("this.format:${this.format}")
    }

    val bgr = this.data ?: let {
        throw IllegalArgumentException("this.data is null")
    }

    val rgba = ByteArray(this.width * this.height * 4)
    for (row in 0 until height) {
        for (column in 0 until width) {
            val blue = bgr[(row * width + column) * 3 + 0]
            val green = bgr[(row * width + column) * 3 + 1]
            val red = bgr[(row * width + column) * 3 + 2]
            val alpha = 255.toByte()

            rgba[(row * width + column) * 4 + 0] = red
            rgba[(row * width + column) * 4 + 1] = green
            rgba[(row * width + column) * 4 + 2] = blue
            rgba[(row * width + column) * 4 + 3] = alpha
        }
    }

    return Image(width, height, rgba, rotate, mirror, ImageFormat.RGBA)
}

fun Image.rgbToRgba(): Image {
    if (this.format != ImageFormat.RGB) {
        throw IllegalArgumentException("this.format:${this.format}")
    }

    val rgb = this.data ?: let {
        throw IllegalArgumentException("this.data is null")
    }

    val rgba = ByteArray(this.width * this.height * 4)
    for (row in 0 until height) {
        for (column in 0 until width) {
            val red = rgb[(row * width + column) * 3 + 0]
            val green = rgb[(row * width + column) * 3 + 1]
            val blue = rgb[(row * width + column) * 3 + 2]
            val alpha = 255.toByte()

            rgba[(row * width + column) * 4 + 0] = red
            rgba[(row * width + column) * 4 + 1] = green
            rgba[(row * width + column) * 4 + 2] = blue
            rgba[(row * width + column) * 4 + 3] = alpha
        }
    }

    return Image(width, height, rgba, rotate, mirror, ImageFormat.RGBA)
}


fun Image.rgbaToArgb(): Image {
    if (this.format != ImageFormat.RGBA) {
        throw IllegalArgumentException("this.format:${this.format}")
    }

    val rgba = this.data ?: let {
        throw IllegalArgumentException("this.data is null")
    }

    val argb = ByteArray(this.width * this.height * 4)
    for (row in 0 until height) {
        for (column in 0 until width) {
            val red = rgba[(row * width + column) * 4 + 0]
            val green = rgba[(row * width + column) * 4 + 1]
            val blue = rgba[(row * width + column) * 4 + 2]
            val alpha = rgba[(row * width + column) * 4 + 3]

            argb[(row * width + column) * 4 + 0] = alpha
            argb[(row * width + column) * 4 + 1] = red
            argb[(row * width + column) * 4 + 2] = green
            argb[(row * width + column) * 4 + 3] = blue
        }
    }

    return Image(width, height, argb, rotate, mirror, ImageFormat.ARGB)
}

fun Image.argbToRgba(): Image {
    if (this.format != ImageFormat.ARGB) {
        throw IllegalArgumentException("this.format:${this.format}")
    }

    val argb = this.data ?: let {
        throw IllegalArgumentException("this.data is null")
    }

    val rgba = ByteArray(this.width * this.height * 4)
    for (row in 0 until height) {
        for (column in 0 until width) {
            val alpha = argb[(row * width + column) * 4 + 0]
            val red = argb[(row * width + column) * 4 + 1]
            val green = argb[(row * width + column) * 4 + 2]
            val blue = argb[(row * width + column) * 4 + 3]

            rgba[(row * width + column) * 4 + 0] = red
            rgba[(row * width + column) * 4 + 1] = green
            rgba[(row * width + column) * 4 + 2] = blue
            rgba[(row * width + column) * 4 + 3] = alpha
        }
    }

    return Image(width, height, rgba, rotate, mirror, ImageFormat.RGBA)
}


fun Image.rgbaToAbgr(): Image {
    if (this.format != ImageFormat.RGBA) {
        throw IllegalArgumentException("this.format:${this.format}")
    }

    val rgba = this.data ?: let {
        throw IllegalArgumentException("this.data is null")
    }

    val abgr = ByteArray(this.width * this.height * 4)
    for (row in 0 until height) {
        for (column in 0 until width) {
            val red = rgba[(row * width + column) * 4 + 0]
            val green = rgba[(row * width + column) * 4 + 1]
            val blue = rgba[(row * width + column) * 4 + 2]
            val alpha = rgba[(row * width + column) * 4 + 3]

            abgr[(row * width + column) * 4 + 0] = alpha
            abgr[(row * width + column) * 4 + 1] = blue
            abgr[(row * width + column) * 4 + 2] = green
            abgr[(row * width + column) * 4 + 3] = red
        }
    }

    return Image(width, height, abgr, rotate, mirror, ImageFormat.ABGR)
}


fun Image.abgrToRgba(): Image {
    if (this.format != ImageFormat.ABGR) {
        throw IllegalArgumentException("this.format:${this.format}")
    }

    val abgr = this.data ?: let {
        throw IllegalArgumentException("this.data is null")
    }

    val rgba = ByteArray(this.width * this.height * 4)
    for (row in 0 until height) {
        for (column in 0 until width) {
            val alpha = abgr[(row * width + column) * 4 + 0]
            val blue = abgr[(row * width + column) * 4 + 1]
            val green = abgr[(row * width + column) * 4 + 2]
            val red = abgr[(row * width + column) * 4 + 3]

            rgba[(row * width + column) * 4 + 0] = red
            rgba[(row * width + column) * 4 + 1] = green
            rgba[(row * width + column) * 4 + 2] = blue
            rgba[(row * width + column) * 4 + 3] = alpha
        }
    }

    return Image(width, height, rgba, rotate, mirror, ImageFormat.RGBA)
}

fun Image.bgraToRgba(): Image {
    if (this.format != ImageFormat.BGRA) {
        throw IllegalArgumentException("this.format:${this.format}")
    }

    val bgra = this.data ?: let {
        throw IllegalArgumentException("this.data is null")
    }

    val rgba = ByteArray(this.width * this.height * 4)
    for (row in 0 until height) {
        for (column in 0 until width) {
            val blue = bgra[(row * width + column) * 4 + 0]
            val green = bgra[(row * width + column) * 4 + 1]
            val red = bgra[(row * width + column) * 4 + 2]
            val alpha = bgra[(row * width + column) * 4 + 3]

            rgba[(row * width + column) * 4 + 0] = red
            rgba[(row * width + column) * 4 + 1] = green
            rgba[(row * width + column) * 4 + 2] = blue
            rgba[(row * width + column) * 4 + 3] = alpha
        }
    }

    return Image(width, height, rgba, rotate, mirror, ImageFormat.RGBA)
}


fun Image.rgbaInputImageToBufferedImage(): BufferedImage? {
    val inputImage = this
    if (inputImage.format != ImageFormat.RGBA) {
        throw IllegalArgumentException("format not RGBA")
    }

    if (inputImage.width <= 0 || inputImage.height <= 0) {
        return null
    }

    // 检查输入数据是否为空
    val srcData = inputImage.data ?: let {
        return null
    }

    // TYPE_4BYTE_ABGR 高位到低位是 A B G R, 在 byte[] 中的排列就是 r g b a
    val bufferedImage = BufferedImage(inputImage.width, inputImage.height, BufferedImage.TYPE_4BYTE_ABGR)

    bufferedImage.raster.setDataElements(0, 0, inputImage.width, inputImage.height, srcData)
    return bufferedImage
}

fun List<Resource>.readAsBufferedImage(): BufferedImage? {
    if (isEmpty()) {
        return null
    }
    val firstImage = this[0].readAsBufferedImage() ?: let {
        return null
    }
    if (size == 1) {
        return firstImage
    }
    return readAsBufferedImage(firstImage.width, firstImage.height)
}

fun List<Resource>.readAsBufferedImage(targetWidth: Int, targetHeight: Int): BufferedImage? {
    if (isEmpty()) {
        return null
    }
    if (size == 1) {
        val firstImage = this[0].readAsBufferedImage() ?: let {
            return null
        }

        if (firstImage.width == targetWidth && firstImage.height == targetHeight) {
            return firstImage
        }
        return firstImage.createAsSize(targetWidth, targetHeight)
    }
    val targetImage = BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB)
    val g2d = targetImage.createGraphics()

    forEach { resource ->
        val bufferedImage = resource.readAsBufferedImage() ?: let {
            return@forEach
        }

        val originalWidth = bufferedImage.width
        val originalHeight = bufferedImage.height
        val originalImage = bufferedImage

        val scaleFactor: Double = Math.min(targetWidth.toDouble() / originalWidth, targetHeight.toDouble() / originalHeight)
        val scaledWidth = (originalWidth * scaleFactor).toInt()
        val scaledHeight = (originalHeight * scaleFactor).toInt()

        // 创建缩放后的图片
        val scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, java.awt.Image.SCALE_SMOOTH)


        // 设置透明度
        val opacity = 0.5f // 设置透明度为50%
        val alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity)
        g2d.composite = alphaComposite

        // 在目标图片上绘制缩放后的图片
        val x: Int = (targetWidth - scaledWidth) / 2
        val y: Int = (targetHeight - scaledHeight) / 2
        g2d.drawImage(scaledImage, x, y, null)
    }

    g2d.dispose()

    return targetImage
}

fun BufferedImage.createAsSize(targetWidth: Int, targetHeight: Int): BufferedImage {
    val originalWidth = this.width
    val originalHeight = this.height
    val originalImage = this

    // TODO:  可以优化,  宽/高 一边相等时可能 scaleFactor == 1 不需要缩放
    val scaleFactor: Double = Math.min(targetWidth.toDouble() / originalWidth, targetHeight.toDouble() / originalHeight)
    val scaledWidth = (originalWidth * scaleFactor).toInt()
    val scaledHeight = (originalHeight * scaleFactor).toInt()

    // 创建缩放后的图片
    val scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, java.awt.Image.SCALE_SMOOTH)
    val targetImage = BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB)

    val g2d = targetImage.createGraphics()

    // 在目标图片上绘制缩放后的图片
    val x: Int = (targetWidth - scaledWidth) / 2
    val y: Int = (targetHeight - scaledHeight) / 2
    g2d.drawImage(scaledImage, x, y, null)
    g2d.dispose()

    return targetImage
}

fun Resource.readAsBufferedImage(): BufferedImage? {
    return use { ImageIO.read(it) }
}


fun List<Pair<Resource, Float>>.readAsBufferedImageWithAlpha(targetWidth: Int, targetHeight: Int): BufferedImage? {
    if (isEmpty()) {
        return null
    }
    if (size == 1) {
        val firstImage = this[0].readAsBufferedImage() ?: let {
            return null
        }

        if (firstImage.width == targetWidth && firstImage.height == targetHeight) {
            return firstImage
        }
        return firstImage.createAsSize(targetWidth, targetHeight)
    }
    val targetImage = BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB)
    val g2d = targetImage.createGraphics()

    forEach { pair ->
        val resource = pair.first
        val alpha = pair.second
        val bufferedImage = resource.readAsBufferedImage() ?: let {
            return@forEach
        }

        val originalWidth = bufferedImage.width
        val originalHeight = bufferedImage.height
        val originalImage = bufferedImage

        val scaleFactor: Double = Math.min(targetWidth.toDouble() / originalWidth, targetHeight.toDouble() / originalHeight)
        val scaledWidth = (originalWidth * scaleFactor).toInt()
        val scaledHeight = (originalHeight * scaleFactor).toInt()

        // 创建缩放后的图片
        val scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, java.awt.Image.SCALE_SMOOTH)

        // 设置透明度
        g2d.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)

        // 在目标图片上绘制缩放后的图片
        val x: Int = (targetWidth - scaledWidth) / 2
        val y: Int = (targetHeight - scaledHeight) / 2
        g2d.drawImage(scaledImage, x, y, null)
    }

    g2d.dispose()

    return targetImage
}

private fun Pair<Resource, Float>.readAsBufferedImage(): BufferedImage? {
    val resource = this.first
    val alpha = this.second
    return resource.readAsBufferedImage(alpha)
}

fun Resource.readAsBufferedImage(alpha: Float): BufferedImage? {
    val originalImage = use { ImageIO.read(it) } ?: let {
        return null
    }
    if (alpha == 1.0f) {
        return originalImage
    }

    // 创建一个新的BufferedImage对象，以便在其上设置透明度
    val transparentImage = BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB)
    val g2d = transparentImage.createGraphics()

    // 设置透明度
    val opacity = alpha
    val alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity)
    g2d.composite = alphaComposite

    // 绘制具有透明度的图片
    g2d.drawImage(originalImage, 0, 0, null)
    g2d.dispose()

    return transparentImage
}


fun BufferedImage.saveAs(path: Path) {
    path.ensureFileExists()
    path.outputStream().use {
        ImageIO.write(this, "png", it)
    }
}

fun BufferedImage.toImage(): Image {
    val width = this.width
    val height = this.height

    if (colorModel is DirectColorModel) {
        val raster = this.raster
        val dataBuffer = raster.dataBuffer

        if (dataBuffer.dataType == DataBufferByte.TYPE_BYTE) {
            val pixelData = (dataBuffer as DataBufferByte).data
            return Image(width, height, pixelData, format = ImageFormat.RGBA)
        } else if (dataBuffer.dataType == DataBufferByte.TYPE_INT) {
            val pixelData = ByteArray(width * height * 4)
            val intPixelData = (dataBuffer as DataBufferInt).data
            var pixelIndex = 0
            for (pixel in intPixelData) {
                pixelData[pixelIndex++] = (pixel shr 16 and 0xFF).toByte() // 获取红色分量
                pixelData[pixelIndex++] = (pixel shr 8 and 0xFF).toByte()  // 获取绿色分量
                pixelData[pixelIndex++] = (pixel and 0xFF).toByte()         // 获取蓝色分量
                pixelData[pixelIndex++] = (pixel shr 24 and 0xFF).toByte() // 获取Alpha分量
            }
            return Image(width, height, pixelData, format = ImageFormat.RGBA)
        } else {
            throw UnsupportedOperationException("not supported yet, dataBuffer.dataType:${dataBuffer.dataType}")
        }
    } else if (colorModel is ComponentColorModel) {
        if (colorModel.numComponents == 3) {
            // RGB 颜色模型，通常是 3 字节表示一个像素
            val dataBuffer = this.raster.dataBuffer ?: let {
                throw IllegalStateException("toAdbImage: dataBuffer is null")
            }
            val dataType = dataBuffer.dataType
            if (dataType == DataBuffer.TYPE_BYTE) {
                val pixelData = (dataBuffer as DataBufferByte).data
                return Image(width, height, pixelData, format = ImageFormat.BGR)
            } else {
                throw UnsupportedOperationException("todo")
            }
        } else if (colorModel.numComponents == 4) {
            // ARGB 颜色模型，通常是 4 字节表示一个像素，第一个字节是 alpha 通道
            val dataBuffer = this.raster.dataBuffer ?: let {
                throw java.lang.IllegalStateException("toAdbImage: dataBuffer is null")
            }
            val dataType = dataBuffer.dataType
            if (dataType == DataBuffer.TYPE_BYTE) {
                val data = (dataBuffer as DataBufferByte).data
                return Image(width, height, data, format = ImageFormat.RGBA)
            } else {
                throw UnsupportedOperationException("todo")
            }
        } else {
            throw UnsupportedOperationException("todo")
        }
    } else {
        throw UnsupportedOperationException("todo")
    }
}