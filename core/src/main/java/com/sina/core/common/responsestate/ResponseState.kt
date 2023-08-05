package com.sina.core.common.responsestate

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


sealed class ResponseState<out T> {
    data class Success<T>(val data: T) : ResponseState<T>()
    data class Error(val exception: Throwable? = null) : ResponseState<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Error -> "Failure ${exception?.message}"
            is Success -> "Success $data"
        }
    }
}

fun <T> Flow<T>.asResponse(): Flow<ResponseState<T>> =
    map<T, ResponseState<T>> {
        ResponseState.Success(it)
    }
        .catch { emit(ResponseState.Error(it)) }

fun <T> Flow<ResponseState<T>>.safeOpen(): Flow<T> = map { response ->
    return@map when (response) {
        is ResponseState.Error -> throw response.exception!!
        is ResponseState.Success -> response.data
    }
}

val <T> ResponseState<T>.isSuccess
    get() = this is ResponseState.Success && data != null

fun <T> Flow<ResponseState<T>>.collectSuccessStateFlow(): Flow<Boolean> {
    return flow {
        collectLatest {
            when (it) {
                is ResponseState.Error -> emit(false)
                is ResponseState.Success -> emit(it.isSuccess)
            }
        }
    }
}
