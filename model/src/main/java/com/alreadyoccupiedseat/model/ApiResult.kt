package com.alreadyoccupiedseat.model

data class ApiResult<T>(
    val code: Int,
    val message: String,
    val data: T
)
