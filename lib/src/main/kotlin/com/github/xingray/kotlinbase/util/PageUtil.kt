package com.github.xingray.kotlinbase.util

object PageUtil {
    fun getLimitStart(pageIndex: Int, pageSize: Int): Int {
        return (pageIndex - 1) * pageSize
    }

    fun getPageCount(totalCount: Long, pageSize: Int): Long {
        return if (totalCount % pageSize == 0L
        ) totalCount / pageSize
        else ((totalCount / pageSize) + 1)
    }
}