package com.alreadyoccupiedseat.model.login

import androidx.annotation.Keep

@Keep
data class ProfileResponse(
    val nickname: String,
    val type: String
)