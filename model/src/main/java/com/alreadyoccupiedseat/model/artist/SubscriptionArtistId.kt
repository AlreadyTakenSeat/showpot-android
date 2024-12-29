package com.alreadyoccupiedseat.model.artist

import androidx.annotation.Keep

@Keep
data class SubscriptionArtistId(
    val spotifyId: String,
    val id: String?
)