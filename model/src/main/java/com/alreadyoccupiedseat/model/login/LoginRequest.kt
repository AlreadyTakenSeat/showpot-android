package com.alreadyoccupiedseat.model.login

import androidx.annotation.Keep

@Keep
data class LoginRequest(
    val fcmToken: String,
    val identifier: String,
    val socialType: String
)