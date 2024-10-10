package com.github.xingray.kotlinbase.value.result

import java.util.function.Function

class TripleResult<T1, T2, T3>(
    val code: Int = CODE_SUCCESS,
    first: T1? = null,
    second: T2? = null,
    third: T3? = null,
    message: String? = MESSAGE_SUCCESS_DEFAULT,
    throwable: Throwable? = null
) {
    val first: T1?
    val second: T2?
    val third: T3?
    val message: String?
    val throwable: Throwable?

    init {
        this.first = first
        this.second = second
        this.third = third
        this.message = message
        this.throwable = throwable
    }

    override fun toString(): String {
        return "TripleResult{" +
                "code=" + code +
                ", first=" + first +
                ", second=" + second +
                ", third=" + third +
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
        val SUCCESS: TripleResult<Any?, Any?, Any?> = TripleResult(CODE_SUCCESS, null, null, null, MESSAGE_SUCCESS_DEFAULT, null)
        val ERROR: TripleResult<Any?, Any?, Any?> = TripleResult(CODE_ERROR_DEFAULT, null, null, null, MESSAGE_ERROR_DEFAULT, null)

        fun <U, V, W> success(): TripleResult<U, V, W> {
            return SUCCESS as TripleResult<U, V, W>
        }

        fun <U, V, W> error(): TripleResult<U, V, W> {
            return ERROR as TripleResult<U, V, W>
        }

        fun <U, V, W> result(success: Boolean): TripleResult<U, V, W> {
            return if (success) success() else error()
        }

        fun <U, V, W, X, Y, Z> copyIgnoreData(result: TripleResult<U, V, W>): TripleResult<X?, Y?, Z?> {
            return TripleResult(result.code, null, null, null, result.message, result.throwable)
        }

        fun <U, V, W, X, Y, Z> copy(result: TripleResult<U, V, W>, firstMapper: Function<U, X>?, secondMapper: Function<V, Y>?, thirdMapper: Function<W, Z>?): TripleResult<X?, Y?, Z?> {
            var targetFirst: X? = null
            val sourceFirst: U? = result.first
            if (sourceFirst != null && firstMapper != null) {
                targetFirst = firstMapper.apply(sourceFirst)
            }

            var targetSecond: Y? = null
            val sourceSecond: V? = result.second
            if (sourceSecond != null && secondMapper != null) {
                targetSecond = secondMapper.apply(sourceSecond)
            }

            var targetThird: Z? = null
            val sourceThird: W? = result.third
            if (sourceThird != null && thirdMapper != null) {
                targetThird = thirdMapper.apply(sourceThird)
            }

            return TripleResult(result.code, targetFirst, targetSecond, targetThird, result.message, result.throwable)
        }

        fun <U, V, W> success(first: U, second: V, third: W): TripleResult<U, V, W> {
            return TripleResult(CODE_SUCCESS, first, second, third, MESSAGE_SUCCESS_DEFAULT, null)
        }

        fun <U, V, W> error(code: Int, message: String?, throwable: Throwable?): TripleResult<U?, V?, W?> {
            return TripleResult(code, null, null, null, message, throwable)
        }

        fun <U, V, W> error(code: Int): TripleResult<U?, V?, W?> {
            return TripleResult(code, null, null, null, MESSAGE_ERROR_DEFAULT, null)
        }

        fun <U, V, W> error(message: String?): TripleResult<U?, V?, W?> {
            return TripleResult(CODE_ERROR_DEFAULT, null, null, null, message, null)
        }

        fun <U, V, W> error(code: Int, message: String?): TripleResult<U?, V?, W?> {
            return TripleResult(code, null, null, null, message, null)
        }

        fun <U, V, W> error(throwable: Throwable): TripleResult<U?, V?, W?> {
            return TripleResult(CODE_ERROR_DEFAULT, null, null, null, throwable.message, throwable)
        }
    }
}
