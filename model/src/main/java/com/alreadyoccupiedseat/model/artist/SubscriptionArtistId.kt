package com.alreadyoccupiedseat.model.artist

import androidx.annotation.Keep

@Keep
data class SubscriptionArtistId(
    val artistSpotifyId: String,
    val id: String?
)