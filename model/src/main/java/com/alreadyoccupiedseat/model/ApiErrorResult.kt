package com.alreadyoccupiedseat.model

import androidx.annotation.Keep

@Keep
data class ApiErrorResult(
    val code: Int,
    val errorCode: String,
    val errorId: String,
    val message: String
)