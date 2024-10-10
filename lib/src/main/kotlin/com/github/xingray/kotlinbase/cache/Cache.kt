package com.github.xingray.coinfarmer.android.filecache

interface Cache {
    fun getString(key: String, defaultValue:String?=null):String?
    fun putString(key: String, value: String)
}