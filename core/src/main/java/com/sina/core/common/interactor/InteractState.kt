package com.sina.core.common.interactor


sealed class InteractState<out T> {
    data class Success<T>(val data: T) : InteractState<T>()
    data class Error(val errorMessage: Exception) : InteractState<Nothing>()
    object Loading : InteractState<Nothing>()

    /**
     * use this later
     * data class NoBodyError(val message: String) : InteractState<Nothing>()
     * data class NetWorkException(val error: Error, val message: String) : InteractState<Nothing>()
     */
}

val <T> InteractState<T>.Success get() = this is InteractState.Success && data != null