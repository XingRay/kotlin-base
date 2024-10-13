package com.github.xingray.kotlinbase.interfaces.function.serializable

import java.io.Serializable
import java.util.function.Function

@FunctionalInterface
interface SFunction<T, R> : Function<T, R>, Serializable