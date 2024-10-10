package com.github.xingray.kotlinbase.value.result

import java.util.function.Function

class DoubleResult<T1, T2>(val code: Int = CODE_SUCCESS, first: T1? = null, second: T2? = null, message: String? = MESSAGE_SUCCESS_DEFAULT, throwable: Throwable? = null) {

    val first: T1?
    val second: T2?
    val message: String?
    val throwable: Throwable?

    init {
        this.first = first
        this.second = second
        this.message = message
        this.throwable = throwable
    }

    override fun toString(): String {
        return "DoubleResult{" +
                "code=" + code +
                ", first=" + first +
                ", second=" + second +
                ", message='" + message + '\'' +
                ", throwable=" + throwable +
                '}'
    }

    val isSuccess: Boolean
        get() = code == CODE_SUCCESS



    companion object {
        const val CODE_SUCCESS: Int = 0
        const val MESSAGE_SUCCESS_DEFAULT: String = "success"
        const val CODE_ERROR_DEFAULT: Int = -1
        const val MESSAGE_ERROR_DEFAULT: String = "error"
        val SUCCESS: DoubleResult<Any?, Any?> = DoubleResult(CODE_SUCCESS, null, null, MESSAGE_SUCCESS_DEFAULT, null)
        val ERROR: DoubleResult<Any?, Any?> = DoubleResult(CODE_ERROR_DEFAULT, null, null, MESSAGE_ERROR_DEFAULT, null)

        fun <U, V> success(): DoubleResult<U, V> {
            return SUCCESS as DoubleResult<U, V>
        }

        fun <U, V> error(): DoubleResult<U, V> {
            return ERROR as DoubleResult<U, V>
        }

        fun <U, V> result(success: Boolean): DoubleResult<U, V> {
            return if (success) success() else error()
        }

        fun <U, V, W, X> copyIgnoreData(result: DoubleResult<U, V>): DoubleResult<W?, X?> {
            return DoubleResult(result.code, null, null, result.message, result.throwable)
        }

        fun <U, V, W, X> copy(result: DoubleResult<U, V>, firstMapper: Function<U, W>?, secondMapper: Function<V, X>?): DoubleResult<W?, X?> {
            var targetFirst: W? = null
            val sourceFirst: U? = result.first
            if (sourceFirst != null && firstMapper != null) {
                targetFirst = firstMapper.apply(sourceFirst)
            }

            var targetSecond: X? = null
            val sourceSecond: V? = result.second
            if (sourceSecond != null && secondMapper != null) {
                targetSecond = secondMapper.apply(sourceSecond)
            }

            return DoubleResult(result.code, targetFirst, targetSecond, result.message, result.throwable)
        }

        fun <U, V> success(first: U, second: V): DoubleResult<U, V> {
            return DoubleResult(CODE_SUCCESS, first, second, MESSAGE_SUCCESS_DEFAULT, null)
        }

        fun <U, V> error(code: Int, message: String?, throwable: Throwable?): DoubleResult<U?, V?> {
            return DoubleResult(code, null, null, message, throwable)
        }

        fun <U, V> error(code: Int): DoubleResult<U?, V?> {
            return DoubleResult(code, null, null, MESSAGE_ERROR_DEFAULT, null)
        }

        fun <U, V> error(message: String?): DoubleResult<U?, V?> {
            return DoubleResult(CODE_ERROR_DEFAULT, null, null, message, null)
        }

        fun <U, V> error(code: Int, message: String?): DoubleResult<U?, V?> {
            return DoubleResult(code, null, null, message, null)
        }

        fun <U, V> error(throwable: Throwable): DoubleResult<U?, V?> {
            return DoubleResult(CODE_ERROR_DEFAULT, null, null, throwable.message, throwable)
        }
    }
}
