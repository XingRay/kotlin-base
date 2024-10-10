package com.github.xingray.kotlinbase.config.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class ConfigKey(val value: String)