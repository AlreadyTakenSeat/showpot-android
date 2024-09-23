package com.alreadyoccupiedseat.model

import androidx.annotation.Keep

@Keep
data class Genre(
    val id: String,
    val name: String,
    val isSubscribed: Boolean
)