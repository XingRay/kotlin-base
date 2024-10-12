package com.github.xingray.kotlinbase.graphic

import kotlin.math.max
import kotlin.math.min


data class Rect(var left: Int = 0, var top: Int = 0, var right: Int = 0, var bottom: Int = 0) {
    val width: Int
        get() = right - left
    val height: Int
        get() = bottom - top

    val isEmpty: Boolean
        get() = left >= right || top >= bottom

    val isValid: Boolean
        get() = left <= right && top <= bottom

    val centerX: Int
        get() = (left + right) shr 1
    val centerY: Int
        get() = (top + bottom) shr 1

    val area: Int
        get() = width * height

    fun copy(): Rect {
        return Rect(left, top, right, bottom)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rect

        if (left != other.left) return false
        if (top != other.top) return false
        if (right != other.right) return false
        if (bottom != other.bottom) return false

        return true
    }

    override fun hashCode(): Int {
        var result = left
        result = 31 * result + top
        result = 31 * result + right
        result = 31 * result + bottom
        return result
    }

    override fun toString(): String {
        return "Rect($left, $top - $right, $bottom)"
    }

    fun offset(dx: Int, dy: Int) {
        left += dx
        top += dy
        right += dx
        bottom += dy
    }

    fun offsetTo(newLeft: Int, newTop: Int) {
        right += newLeft - left
        bottom += newTop - top
        left = newLeft
        top = newTop
    }

    fun contains(x: Int, y: Int): Boolean {
        return left < right && top < bottom && x >= left && x < right && y >= top && y < bottom
    }

    fun contains(left: Int, top: Int, right: Int, bottom: Int): Boolean {
        return this.left < this.right && (this.top < this.bottom)
                && (this.left <= left) && (this.top <= top)
                && (this.right >= right) && (this.bottom >= bottom)
    }

    fun contains(rect: Rect): Boolean {
        return this.left < this.right && (this.top < this.bottom)
                && (left <= rect.left) && (top <= rect.top)
                && (right >= rect.right) && (bottom >= rect.bottom)
    }

    fun isIntersects(rect: Rect): Boolean {
        return left < rect.right && rect.left < right && top < rect.bottom && rect.top < bottom;
    }

    fun isIntersects(left: Int, top: Int, right: Int, bottom: Int): Boolean {
        return this.left < right && (left < this.right) && (this.top < bottom) && (top < this.bottom)
    }

    fun intersectUnchecked(other: Rect): Rect {
        val left = max(left, other.left)
        val top = max(top, other.top)
        val right = min(right, other.right)
        val bottom = min(bottom, other.bottom)

        return Rect(left, top, right, bottom)
    }

    fun intersect(other: Rect): Rect? {
        if (isIntersects(other)) {
            return intersectUnchecked(other)
        } else {
            return null
        }
    }

    fun setByIntersectUnchecked(other: Rect) {
        left = max(left, other.left)
        top = max(top, other.top)
        right = min(right, other.right)
        bottom = min(bottom, other.bottom)
    }

    fun setByIntersect(other: Rect): Boolean {
        if (isIntersects(other)) {
            setByIntersectUnchecked(other)
            return true
        } else {
            return false
        }
    }

    fun union(left: Int, top: Int, right: Int, bottom: Int) {
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

    fun union(r: Rect) {
        union(r.left, r.top, r.right, r.bottom)
    }

    fun union(x: Int, y: Int) {
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

    fun splitVertically(vararg splits: Rect) {
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

    fun splitHorizontally(vararg outSplits: Rect) {
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
            left = (left * scale + 0.5f).toInt()
            top = (top * scale + 0.5f).toInt()
            right = (right * scale + 0.5f).toInt()
            bottom = (bottom * scale + 0.5f).toInt()
        }
    }
}







