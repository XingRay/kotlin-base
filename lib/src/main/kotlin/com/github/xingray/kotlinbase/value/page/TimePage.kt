package com.github.xingray.kotlinbase.value.page

import java.util.*

class TimePage<T> {
    private var total: Long = 0

    private var timestamp: Long = 0

    private var pageSize: Long = 0

    private var items: List<T>? = null

    fun setTotal(total: Long) {
        this.total = total
    }

    fun setTimestamp(timestamp: Long) {
        this.timestamp = timestamp
    }

    fun setPageSize(pageSize: Long) {
        this.pageSize = pageSize
    }

    fun setItems(items: List<T>?) {
        this.items = items
    }

    fun getTotal(): Long {
        return total
    }

    fun getTimestamp(): Long {
        return timestamp
    }

    fun getPageSize(): Long {
        return pageSize
    }

    fun getItems(): List<T> {
        return Collections.unmodifiableList(items)
    }

    val totalPageCount: Long
        get() = total / pageSize + (if (total % pageSize == 0L) 0 else 1)

    val itemCount: Long
        get() = if (items == null) 0 else items!!.size.toLong()

    override fun toString(): String {
        return "TimePage{" +
                "total=" + total +
                ", timestamp=" + timestamp +
                ", pageSize=" + pageSize +
                ", items=" + items +
                '}'
    }

    companion object {
        fun <E> of(pageSize: Long, timestamp: Long, total: Long, items: List<E>?): TimePage<E> {
            require(pageSize > 0) { "pageSize must greater than 0" }
            require(total >= 0) { "total must greater than or equals 0" }
            val page = TimePage<E>()
            page.pageSize = pageSize
            page.timestamp = timestamp
            page.total = total
            if (items != null) {
                page.items = ArrayList(items)
            }
            return page
        }
    }
}
