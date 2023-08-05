package com.sina.core.network.response

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.Response

fun <T> Response<T>.openResponse(): T {
    return if (isSuccessful) {
        val body = body()
        when {
            code() in 200..201 && body != null -> body
            body == null -> throw NoBodyException()
            else -> throw Exception()
        }
    } else {
        val errorBody = errorBody()
        val networkError = Gson().fromJson(errorBody?.charStream(), NetworkError::class.java)
        throw NetworkException(error = networkError.asError())
    }
}

data class NetworkError(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String,
    val data: NetworkStatus
)
data class NetworkStatus(
    @SerializedName("status")
    val status: Int
)
class NoBodyException(msg: String? = null) : Exception(msg)
class NetworkException(val error: Error, msg: String? = null) : Exception(msg)

data class Error(
    val code: String,
    val message: String,
    val data: Status
)

data class Status(
    val code: Int
)

fun NetworkError.asError() = Error(
    code = code,
    message = message,
    data = data.asStatus()
)

fun NetworkStatus.asStatus() = Status(status)