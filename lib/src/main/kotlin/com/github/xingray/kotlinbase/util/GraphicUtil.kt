package com.github.xingray.kotlinbase.util

import com.github.xingray.kotlinbase.graphic.RectDouble

object GraphicUtil {

    fun calcRectInCenterOfParent(parentWidth: Double, parentHeight: Double, childWidth: Double, childHeight: Double): RectDouble {
        if (parentWidth * childHeight == parentHeight * childWidth) {
            return RectDouble(0.0, 0.0, parentWidth, parentHeight)
        }

        val parentRatio = parentWidth / parentHeight
        val childRatio = childWidth / childHeight

        if (parentRatio > childRatio) {
            // 图片比较宽，宽度适配 StackPane，高度根据比例调整
            val height = parentHeight
            val width = height * childRatio
            return RectDouble(left = (parentWidth - width) / 2, top = 0.0, right = (parentWidth + width) / 2, bottom = parentHeight)
        } else {
            // 图片比较高，适配高度，高度等于 StackPane，高度根据比例调整
            val width = parentWidth
            val height = width / childRatio
            return RectDouble(left = 0.0, top = (parentHeight - height) / 2, right = parentWidth, bottom = (parentHeight + height) / 2)
        }
    }
}