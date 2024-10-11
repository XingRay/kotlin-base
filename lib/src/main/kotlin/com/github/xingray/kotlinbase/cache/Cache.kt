package com.github.xingray.kotlinbase.cache

interface Cache {
    fun getString(key: String, defaultValue:String?=null):String?
    fun putString(key: String, value: String)
}