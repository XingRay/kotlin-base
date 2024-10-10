package com.github.xingray.kotlinbase.config.annotations

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ConfigType(val value: Int) {

    companion object {
        const val PROPERTIES = 0
        const val JSON = 1
    }
}