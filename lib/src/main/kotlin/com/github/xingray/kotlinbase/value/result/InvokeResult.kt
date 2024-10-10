package com.github.xingray.kotlinbase.value.result

import java.util.function.Function

class InvokeResult<T, E>(val code: Int = CODE_SUCCESS, data: T? = null, error: E? = null, message: String? = MESSAGE_SUCCESS_DEFAULT, throwable: Throwable? = null) {
    val data: T?
    val error: E?
    val message: String?
    val throwable: Throwable?

    init {
        this.data = data
        this.error = error
        this.message = message
        this.throwable = throwable
    }

    override fun toString(): String {
        return "InvokeResult{" +
                "code=" + code +
                ", data=" + data +
                ", error=" + error +
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
        val SUCCESS: InvokeResult<Any?, Any?> = InvokeResult(CODE_SUCCESS, null, null, MESSAGE_SUCCESS_DEFAULT, null)
        val ERROR: InvokeResult<Any?, Any?> = InvokeResult(CODE_ERROR_DEFAULT, null, null, MESSAGE_ERROR_DEFAULT, null)

        fun <U, V> success(): InvokeResult<U, V> {
            return SUCCESS as InvokeResult<U, V>
        }

        fun <U, V> error(): InvokeResult<U, V> {
            return ERROR as InvokeResult<U, V>
        }

        fun <U, V> result(success: Boolean): InvokeResult<U, V> {
            return if (success) success() else error()
        }

        fun <V, U, W> copyIgnoreData(result: InvokeResult<U, W>): InvokeResult<V?, W> {
            return InvokeResult(result.code, null, result.error, result.message, result.throwable)
        }

        fun <V, U, W> copy(result: InvokeResult<U, W>, mapper: Function<U?, V>): InvokeResult<V?, W> {
            var data: V? = null
            if (result.isSuccess) {
                data = mapper.apply(result.data)
            }
            return InvokeResult(result.code, data, result.error, result.message, result.throwable)
        }

        fun <U, V> success(data: U): InvokeResult<U, V?> {
            return InvokeResult(CODE_SUCCESS, data, null, MESSAGE_SUCCESS_DEFAULT, null)
        }

        fun <U, V> error(code: Int, error: V, message: String?, throwable: Throwable?): InvokeResult<U?, V> {
            return InvokeResult(code, null, error, message, throwable)
        }

        fun <U, V> error(code: Int, message: String?, throwable: Throwable?): InvokeResult<U?, V?> {
            return InvokeResult(code, null, null, message, throwable)
        }

        fun <U, V> error(code: Int): InvokeResult<U?, V?> {
            return InvokeResult(code, null, null, MESSAGE_ERROR_DEFAULT, null)
        }

        fun <U, V> error(message: String?): InvokeResult<U?, V?> {
            return InvokeResult(CODE_ERROR_DEFAULT, null, null, message, null)
        }

        fun <U, V> error(code: Int, message: String?): InvokeResult<U?, V?> {
            return InvokeResult(code, null, null, message, null)
        }

        fun <U, V> error(throwable: Throwable): InvokeResult<U?, V?> {
            return InvokeResult(CODE_ERROR_DEFAULT, null, null, throwable.message, throwable)
        }

        fun <U, V> error(error: V): InvokeResult<U?, V> {
            return InvokeResult(CODE_ERROR_DEFAULT, null, error, MESSAGE_ERROR_DEFAULT, null)
        }

        fun <U, V> error(result: InvokeResult<*, V>): InvokeResult<U?, V> {
            return InvokeResult(result.code, null, result.error, result.message, result.throwable)
        }
    }
}
