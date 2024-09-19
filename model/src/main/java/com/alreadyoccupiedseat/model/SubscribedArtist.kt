package com.alreadyoccupiedseat.model

import androidx.annotation.Keep

@Keep
data class SubscribedArtist(
    val id: String,
    val imageURL: String,
    val koreanName: String,
    val englishName: String,
    val isSubscribed: Boolean,
)

