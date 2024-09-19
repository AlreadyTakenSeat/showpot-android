package com.alreadyoccupiedseat.model.login

import androidx.annotation.Keep

@Keep
data class TokenReIssueRequest(
    val refreshToken: String
)