package com.alreadyoccupiedseat.model

import androidx.annotation.Keep

@Keep
data class ApiResult<T>(
    val code: Int,
    val message: String,
    val data: T
)
