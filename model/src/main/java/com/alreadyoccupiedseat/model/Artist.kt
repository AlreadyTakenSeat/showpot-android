package com.alreadyoccupiedseat.model

import androidx.annotation.Keep

@Keep
data class Artist(
    val id: String,
    val imageURL: String,
    val name: String,
)