package com.github.xingray.kotlinbase.value.result

import java.util.function.Function

class Result<T>(val code: Int = CODE_SUCCESS, data: T? = null, message: String? = MESSAGE_SUCCESS_DEFAULT, throwable: Throwable? = null) {

    val data: T?
    val message: String?
    val throwable: Throwable?

    init {
        this.data = data
        this.message = message
        this.throwable = throwable
    }

    val isSuccess: Boolean
        get() = code == CODE_SUCCESS

    override fun toString(): String {
        return "Result{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", throwable=" + throwable +
                '}'
    }

    companion object {
        const val CODE_SUCCESS: Int = 0
        const val MESSAGE_SUCCESS_DEFAULT: String = "success"
        const val CODE_ERROR_DEFAULT: Int = -1
        const val MESSAGE_ERROR_DEFAULT: String = "error"
        val SUCCESS: Result<Any?> = Result(CODE_SUCCESS, null, MESSAGE_SUCCESS_DEFAULT, null)
        val ERROR: Result<Any?> = Result(CODE_ERROR_DEFAULT, null, MESSAGE_ERROR_DEFAULT, null)

        fun <U> success(): Result<U> {
            return SUCCESS as Result<U>
        }

        fun <U> error(): Result<U> {
            return ERROR as Result<U>
        }

        fun <U> result(success: Boolean): Result<U> {
            return if (success) success() else error()
        }

        fun <V, U> copyIgnoreData(result: Result<U>): Result<V?> {
            return Result(result.code, null, result.message, result.throwable)
        }

        fun <V, U> copy(result: Result<U>, mapper: Function<U, V>): Result<V?> {
            var data: V? = null
            val sourceData: U? = result.data
            if (sourceData != null) {
                data = mapper.apply(sourceData)
            }
            return Result(result.code, data, result.message, result.throwable)
        }

        fun <U> success(data: U): Result<U> {
            return Result(CODE_SUCCESS, data, MESSAGE_SUCCESS_DEFAULT, null)
        }

        fun <U> error(code: Int, message: String?, throwable: Throwable?): Result<U?> {
            return Result(code, null, message, throwable)
        }

        fun <U> error(code: Int): Result<U?> {
            return Result(code, null, MESSAGE_ERROR_DEFAULT, null)
        }

        fun <U> error(message: String?): Result<U?> {
            return Result(CODE_ERROR_DEFAULT, null, message, null)
        }

        fun <U> error(code: Int, message: String?): Result<U?> {
            return Result(code, null, message, null)
        }

        fun <U> error(throwable: Throwable): Result<U?> {
            return Result(CODE_ERROR_DEFAULT, null, throwable.message, throwable)
        }
    }

}
