package com.github.xingray.kotlinbase.value.page

import java.util.*

class Page<T> {
    private var total: Long = 0

    private var pageIndex: Long = 0

    private var pageSize: Long = 0

    private var items: List<T>? = null

    fun setTotal(total: Long) {
        this.total = total
    }

    fun setPageIndex(pageIndex: Long) {
        this.pageIndex = pageIndex
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

    fun getPageIndex(): Long {
        return pageIndex
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
        return "Page{" +
                "total=" + total +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", items=" + items +
                '}'
    }

    companion object {
        fun <E> of(pageIndex: Long, pageSize: Long, total: Long, items: List<E>?): Page<E> {
            require(pageIndex > 0) { "pageIndex must greater than 0" }
            require(pageSize > 0) { "pageSize must greater than 0" }
            require(total >= 0) { "total must greater than or equals 0" }

            val page = Page<E>()
            page.pageIndex = pageIndex
            page.pageSize = pageSize
            page.total = total
            if (items != null) {
                page.items = ArrayList(items)
            }

            return page
        }
    }
}
