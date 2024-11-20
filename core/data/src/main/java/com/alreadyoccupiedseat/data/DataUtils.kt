package com.alreadyoccupiedseat.data

import com.alreadyoccupiedseat.model.ApiErrorResult
import com.google.gson.Gson
import retrofit2.Response

fun <T, R> Response<T>.getResult(transform: (T) -> R): R {

    if (this.isSuccessful.not())
        throw Exception(this.errorBody()?.string())
    else if (this.body() == null)
        throw Exception(this.message())

    return requireNotNull(this.body()?.let(transform) ?: throw Exception("empty response"))
}

// only Can be used from getResult
fun Throwable.toApiErrorResult(): ApiErrorResult {
    return Gson().fromJson(
        requireNotNull(this.message),
        ApiErrorResult::class.java
    )
}