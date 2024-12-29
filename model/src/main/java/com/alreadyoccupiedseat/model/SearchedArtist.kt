package com.alreadyoccupiedseat.model

import androidx.annotation.Keep

@Keep
data class SearchedArtist(
    val id: String?,
    val imageURL: String,
    val name: String,
    val spotifyId: String,
    val isSubscribed: Boolean,
)

