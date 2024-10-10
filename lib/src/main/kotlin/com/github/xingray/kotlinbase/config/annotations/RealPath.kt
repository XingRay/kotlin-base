package com.github.xingray.kotlinbase.config.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class RealPath(val value: String)