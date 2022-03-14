package com.wrondon.androidtaskdemo.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class MyResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : MyResult<T>()
    data class Invalid(val message: String) : MyResult<Nothing>()
    data class Error(val exception: Exception) : MyResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Invalid -> "Invalid [  message  =  $message  ]"
            is Error -> "Error[exception=$exception]"
        }
    }
}