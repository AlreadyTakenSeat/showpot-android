package com.alreadyoccupiedseat.model.login

import androidx.annotation.Keep

@Keep
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)