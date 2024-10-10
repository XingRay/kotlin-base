package com.github.xingray.kotlinbase.command.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class ListValueSeparator(val value: String)
