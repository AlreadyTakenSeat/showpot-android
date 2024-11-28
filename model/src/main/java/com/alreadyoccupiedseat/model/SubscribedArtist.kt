package com.alreadyoccupiedseat.model

import androidx.annotation.Keep

@Keep
data class SubscribedArtist(
    val id: String?,
    val imageURL: String,
    val name: String,
    val artistSpotifyId: String,
    val isSubscribed: Boolean,
)

