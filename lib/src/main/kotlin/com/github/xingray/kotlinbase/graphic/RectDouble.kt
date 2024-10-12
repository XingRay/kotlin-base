package com.github.xingray.kotlinbase.graphic

import kotlin.math.max
import kotlin.math.min

data class RectDouble(var left: Double = 0.0, var top: Double = 0.0, var right: Double = 0.0, var bottom: Double = 0.0) {
    val width: Double
        get() = right - left
    val height: Double
        get() = bottom - top

    val isEmpty: Boolean
        get() = left >= right || top >= bottom

    val isValid: Boolean
        get() = left <= right && top <= bottom

    val centerX: Double
        get() = (left + right) * 0.5
    val centerY: Double
        get() = (top + bottom) * 0.5

    val area: Double
        get() = width * height

    fun copy(): RectDouble {
        return RectDouble(left, top, right, bottom)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RectDouble

        if (left != other.left) return false
        if (top != other.top) return false
        if (right != other.right) return false
        if (bottom != other.bottom) return false

        return true
    }

    override fun hashCode(): Int {
        var result = left.hashCode()
        result = 31 * result + top.hashCode()
        result = 31 * result + right.hashCode()
        result = 31 * result + bottom.hashCode()
        return result
    }

    override fun toString(): String {
        return "RectDouble($left, $top - $right, $bottom)"
    }

    fun offset(dx: Double, dy: Double) {
        left += dx
        top += dy
        right += dx
        bottom += dy
    }

    fun offsetTo(newLeft: Double, newTop: Double) {
        right += newLeft - left
        bottom += newTop - top
        left = newLeft
        top = newTop
    }

    fun contains(x: Double, y: Double): Boolean {
        return left < right && top < bottom && x >= left && x < right && y >= top && y < bottom
    }

    fun contains(left: Double, top: Double, right: Double, bottom: Double): Boolean {
        return this.left < this.right && (this.top < this.bottom)
                && (this.left <= left) && (this.top <= top)
                && (this.right >= right) && (this.bottom >= bottom)
    }

    fun contains(rect: RectDouble): Boolean {
        return this.left < this.right && (this.top < this.bottom)
                && (left <= rect.left) && (top <= rect.top)
                && (right >= rect.right) && (bottom >= rect.bottom)
    }

    fun isIntersects(rect: RectDouble): Boolean {
        return left < rect.right && rect.left < right && top < rect.bottom && rect.top < bottom;
    }

    fun isIntersects(left: Double, top: Double, right: Double, bottom: Double): Boolean {
        return this.left < right && (left < this.right) && (this.top < bottom) && (top < this.bottom)
    }

    fun intersectUnchecked(other: RectDouble): RectDouble {
        val left = max(left, other.left)
        val top = max(top, other.top)
        val right = min(right, other.right)
        val bottom = min(bottom, other.bottom)

        return RectDouble(left, top, right, bottom)
    }

    fun intersect(other: RectDouble): RectDouble? {
        if (isIntersects(other)) {
            return intersectUnchecked(other)
        } else {
            return null
        }
    }

    fun setByIntersectUnchecked(other: RectDouble) {
        left = max(left, other.left)
        top = max(top, other.top)
        right = min(right, other.right)
        bottom = min(bottom, other.bottom)
    }

    fun setByIntersect(other: RectDouble): Boolean {
        if (isIntersects(other)) {
            setByIntersectUnchecked(other)
            return true
        } else {
            return false
        }
    }

    fun union(left: Double, top: Double, right: Double, bottom: Double) {
        if ((left < right) && (top < bottom)) {
            if ((this.left < this.right) && (this.top < this.bottom)) {
                if (this.left > left) this.left = left
                if (this.top > top) this.top = top
                if (this.right < right) this.right = right
                if (this.bottom < bottom) this.bottom = bottom
            } else {
                this.left = left
                this.top = top
                this.right = right
                this.bottom = bottom
            }
        }
    }

    fun union(r: RectDouble) {
        union(r.left, r.top, r.right, r.bottom)
    }

    fun union(x: Double, y: Double) {
        if (x < left) {
            left = x
        } else if (x > right) {
            right = x
        }
        if (y < top) {
            top = y
        } else if (y > bottom) {
            bottom = y
        }
    }

    fun sort() {
        if (left > right) {
            val temp = left
            left = right
            right = temp
        }
        if (top > bottom) {
            val temp = top
            top = bottom
            bottom = temp
        }
    }

    fun splitVertically(vararg splits: RectDouble) {
        val count = splits.size
        val splitWidth = width / count
        for (i in 0 until count) {
            val split = splits[i]
            split.left = left + (splitWidth * i)
            split.top = top
            split.right = split.left + splitWidth
            split.bottom = bottom
        }
    }

    fun splitHorizontally(vararg outSplits: RectDouble) {
        val count = outSplits.size
        val splitHeight = height / count
        for (i in 0 until count) {
            val split = outSplits[i]
            split.left = left
            split.top = top + (splitHeight * i)
            split.right = right
            split.bottom = split.top + splitHeight
        }
    }

    fun scale(scale: Float) {
        if (scale != 1.0f) {
            left = left * scale
            top = top * scale
            right = right * scale
            bottom = bottom * scale
        }
    }
}