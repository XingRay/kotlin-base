package com.github.xingray.kotlinbase.value.result

import java.util.function.Function

class RemoteInvokeResult<T, E> @JvmOverloads constructor(val code: Int = CODE_SUCCESS, data: T? = null, error: E? = null, message: String = MESSAGE_SUCCESS_DEFAULT) {
    val data: T?
    val error: E?
    val message: String

    override fun toString(): String {
        return "InvokeResult{" +
                "code=" + code +
                ", data=" + data +
                ", error=" + error +
                ", message='" + message + '\'' +
                '}'
    }

    val isSuccess: Boolean
        get() = code == CODE_SUCCESS

    init {
        this.data = data
        this.error = error
        this.message = message
    }

    companion object {
        const val CODE_SUCCESS: Int = 0
        const val MESSAGE_SUCCESS_DEFAULT: String = "success"
        const val CODE_ERROR_DEFAULT: Int = -1
        const val MESSAGE_ERROR_DEFAULT: String = "error"
        val SUCCESS: RemoteInvokeResult<Any?, Any?> = RemoteInvokeResult(CODE_SUCCESS, null, null, MESSAGE_SUCCESS_DEFAULT)
        val ERROR: RemoteInvokeResult<Any?, Any?> = RemoteInvokeResult(CODE_ERROR_DEFAULT, null, null, MESSAGE_ERROR_DEFAULT)

        fun <U, V> success(): RemoteInvokeResult<U, V> {
            return SUCCESS as RemoteInvokeResult<U, V>
        }

        fun <U, V> error(): RemoteInvokeResult<U, V> {
            return ERROR as RemoteInvokeResult<U, V>
        }

        fun <U, V> result(success: Boolean): RemoteInvokeResult<U, V> {
            return if (success) success() else error()
        }

        fun <V, U, W> copyIgnoreData(result: RemoteInvokeResult<U, W>): RemoteInvokeResult<V?, W> {
            return RemoteInvokeResult(result.code, null, result.error, result.message)
        }

        fun <V, U, W> copy(result: RemoteInvokeResult<U, W>, mapper: Function<U?, V>): RemoteInvokeResult<V?, W> {
            var data: V? = null
            if (result.isSuccess) {
                data = mapper.apply(result.data)
            }
            return RemoteInvokeResult(result.code, data, result.error, result.message)
        }

        fun <U, V> success(data: U): RemoteInvokeResult<U, V?> {
            return RemoteInvokeResult(CODE_SUCCESS, data, null, MESSAGE_SUCCESS_DEFAULT)
        }

        fun <U, V> error(code: Int, error: V, message: String): RemoteInvokeResult<U?, V> {
            return RemoteInvokeResult(code, null, error, message)
        }

        fun <U, V> error(code: Int): RemoteInvokeResult<U?, V?> {
            return RemoteInvokeResult(code, null, null, MESSAGE_ERROR_DEFAULT)
        }

        fun <U, V> error(message: String): RemoteInvokeResult<U?, V?> {
            return RemoteInvokeResult(CODE_ERROR_DEFAULT, null, null, message)
        }

        fun <U, V> error(code: Int, message: String): RemoteInvokeResult<U?, V?> {
            return RemoteInvokeResult(code, null, null, message)
        }

        fun <U, V> error(error: V): RemoteInvokeResult<U?, V> {
            return RemoteInvokeResult(CODE_ERROR_DEFAULT, null, error, MESSAGE_ERROR_DEFAULT)
        }

        fun <U, V> error(result: RemoteInvokeResult<*, V>): RemoteInvokeResult<U?, V> {
            return RemoteInvokeResult(result.code, null, result.error, result.message)
        }
    }
}
