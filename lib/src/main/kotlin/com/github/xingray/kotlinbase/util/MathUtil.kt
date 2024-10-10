package com.github.xingray.kotlinbase.util

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

object MathUtil {
    /**
     * 将一段范围(min~max)的数字拆分为n等分，获得每等分的差值，并且该差值乘以段数可能会略小于max-min，该差值尽可能取整
     */
    fun getSplitRange(min: Double, max: Double, n: Int): Double {
        require(!(min > max || n < 0))

        val split = (max - min) / (n * 10)
        return reserveValidNumber(split, 2) * 10
    }

    /**
     * 保留N位有效数字，不会进行四舍五入，后面的数直接抹去
     */
    fun reserveValidNumber(v: Double, n: Int): Double {
        //v=242.345 n=2
        //log10=2.xx
        var v = v
        if (v == 0.0) {
            return 0.0
        }
        var minus = false
        if (v < 0) {
            minus = true
            v = -v
        }
        val log10 = log10(v)

        //3
        val ceil = ceil(log10).toInt()
        //2
        val floor = floor(log10).toInt()


        var value = 0.0
        for (i in 0 until n) {
            val pow: Double = 10.0.pow((floor - i).toDouble())
            val v1 = ((v / pow).toInt()) * pow
            value += v1
            v -= v1
        }

        return if (minus) {
            -value
        } else {
            value
        }
    }
}