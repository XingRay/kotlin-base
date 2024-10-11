package com.nanosecond.makeup.android.collection

open class CursorList<T>(list: List<T>) {
    private val mList = mutableListOf<T>()
    private var mCursor = 0
    val current: T
        get() {
            if (mList.isEmpty()) {
                throw IllegalStateException("list is empty, must add some elements before get current element!")
            }
            return mList[mCursor]
        }

    init {
        mList.addAll(list)
    }

    fun previous(): T {
        if (mList.isEmpty()) {
            throw IllegalStateException("list is empty, add some elements before move cursor")
        }
        mCursor = (mCursor - 1 + mList.size) % mList.size
        return current
    }

    fun next(): T {
        if (mList.isEmpty()) {
            throw IllegalStateException("list is empty, add some elements before move cursor")
        }
        mCursor = (mCursor + 1) % mList.size
        return current
    }

    fun first(): T {
        if (mList.isEmpty()) {
            throw IllegalStateException("list is empty, add some elements before move cursor")
        }
        mCursor = 0
        return current
    }

    fun last(): T {
        if (mList.isEmpty()) {
            throw IllegalStateException("list is empty, add some elements before move cursor")
        }
        mCursor = mList.size - 1
        return current
    }

    fun add(e: T) {
        mList.add(e)
    }

    fun addAll(elements: Collection<T>) {
        mList.addAll(elements)
    }
}