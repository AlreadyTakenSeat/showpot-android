package com.alreadyoccupiedseat.model

data class ApiErrorResult(
    val code: Int,
    val errorCode: String,
    val errorId: String,
    val message: String
)