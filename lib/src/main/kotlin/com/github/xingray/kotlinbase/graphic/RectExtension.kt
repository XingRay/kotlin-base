package com.github.xingray.kotlinbase.graphic

fun calcRectInCenterOfParent(parentWidth: Double, parentHeight: Double, targetWidth: Double, targetHeight: Double): RectDouble {
    val parentRatio = parentWidth / parentHeight
    val targetRatio = targetWidth / targetHeight

    if (parentRatio > targetRatio) {
        // 图片比较宽，宽度适配 StackPane，高度根据比例调整
        val width = parentWidth
        val height = parentWidth / parentRatio
        return RectDouble(0.0, (parentHeight - height) / 2, width, (parentHeight + height) / 2)
    } else {
        // 图片比较高，适配高度，高度等于 StackPane，高度根据比例调整
        val height = parentHeight
        val width = parentHeight * parentRatio
        return RectDouble((parentWidth - width) / 2, 0.0, (parentWidth + width) / 2, height)
    }
}