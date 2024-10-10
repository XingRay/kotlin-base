package com.github.xingray.kotlinbase.timer

import com.github.xingray.kotlinbase.util.TimeUtil

object Timer {

    @JvmStatic
    private val TAG = Timer::class.java.simpleName

    var logger: (String, String, Long) -> Unit = { tag, msg, mills ->
        print("$tag $msg $mills ms")
    }

    fun <T> recordTime(tag: String = TAG, msg: String = "recordTime", enable: Boolean = true, task: () -> T): T {
        if (enable) {
            val start = TimeUtil.nowMills()
            val t = task()
            val timeMills = TimeUtil.nowMills() - start
            logger(tag, msg, timeMills)
            return t
        } else {
            return task()
        }
    }
}