package com.github.xingray.kotlinbase.command.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.TYPE)
annotation class KeyValueLinker(val value: String)