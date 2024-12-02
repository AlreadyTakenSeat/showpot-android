package com.alreadyoccupiedseat.model

import androidx.annotation.Keep

@Keep
data class SearchedArtist(
    val id: String?,
    val imageURL: String,
    val name: String,
    val artistSpotifyId: String,
    val isSubscribed: Boolean,
)

