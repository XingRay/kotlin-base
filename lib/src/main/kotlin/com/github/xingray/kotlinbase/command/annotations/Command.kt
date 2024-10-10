package com.github.xingray.kotlinbase.command.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Command(val value:String)
