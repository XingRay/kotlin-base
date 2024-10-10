package com.github.xingray.kotlinbase.value.result

import java.util.function.Function

class RemoteResult<T> @JvmOverloads constructor(val code: Int = CODE_SUCCESS, data: T? = null, message: String = MESSAGE_SUCCESS_DEFAULT) {
    val data: T?
    val message: String

    init {
        this.data = data
        this.message = message
    }

    override fun toString(): String {
        return "RemoteResult{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}'
    }

    val isSuccess: Boolean
        get() = code == CODE_SUCCESS


    companion object {
        const val CODE_SUCCESS: Int = 0
        const val MESSAGE_SUCCESS_DEFAULT: String = "success"
        const val CODE_ERROR_DEFAULT: Int = -1
        const val MESSAGE_ERROR_DEFAULT: String = "error"
        val SUCCESS: RemoteResult<Any?> = RemoteResult(CODE_SUCCESS, null, MESSAGE_SUCCESS_DEFAULT)
        val ERROR: RemoteResult<Any?> = RemoteResult(CODE_ERROR_DEFAULT, null, MESSAGE_ERROR_DEFAULT)

        fun <U> success(): RemoteResult<U> {
            return SUCCESS as RemoteResult<U>
        }

        fun <U> error(): RemoteResult<U> {
            return ERROR as RemoteResult<U>
        }

        fun <U> result(success: Boolean): RemoteResult<U> {
            return if (success) success() else error()
        }

        fun <V, U> copyIgnoreData(result: RemoteResult<U>): RemoteResult<V?> {
            return RemoteResult(result.code, null, result.message)
        }

        fun <V, U> copy(result: RemoteResult<U>, mapper: Function<U, V>): RemoteResult<V?> {
            var data: V? = null
            val sourceData: U? = result.data
            if (sourceData != null) {
                data = mapper.apply(sourceData)
            }
            return RemoteResult(result.code, data, result.message)
        }

        fun <U> success(data: U): RemoteResult<U> {
            return RemoteResult(CODE_SUCCESS, data, MESSAGE_SUCCESS_DEFAULT)
        }

        fun <U> error(code: Int): RemoteResult<U?> {
            return RemoteResult(code, null, MESSAGE_ERROR_DEFAULT)
        }

        fun <U> error(message: String): RemoteResult<U?> {
            return RemoteResult(CODE_ERROR_DEFAULT, null, message)
        }

        fun <U> error(code: Int, message: String): RemoteResult<U?> {
            return RemoteResult(code, null, message)
        }
    }
}
