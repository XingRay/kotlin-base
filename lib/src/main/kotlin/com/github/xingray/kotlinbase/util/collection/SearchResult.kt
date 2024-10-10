package com.github.xingray.kotlinbase.util.collection

import java.util.*

class SearchResult<T>() {
    private var result: T? = null
    private var indexList: MutableList<Int>

    init {
        indexList = ArrayList()
    }

    constructor(result: T, vararg index: Int) : this() {
        this.result = result
        if (index != null) {
            indexList.addAll(index.toList())
        }
    }

    constructor(result: T, indexList: List<Int>?) : this() {
        this.result = result
        if (indexList != null) {
            this.indexList.addAll(indexList)
        }
    }

    fun getResult(): T? {
        return result
    }

    fun setResult(result: T) {
        this.result = result
    }

    fun getIndexList(): List<Int> {
        return indexList
    }

    fun setIndexList(indexList: MutableList<Int>) {
        this.indexList = indexList
    }

    override fun toString(): String {
        return ("\"SearchResult\": {"
                + "\"result\": \"" + result
                + ", \"indexList\": \"" + indexList
                + '}')
    }
}
