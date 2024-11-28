package com.alreadyoccupiedseat.model.artist

import androidx.annotation.Keep

@Keep
data class SubscribeArtistsResponse(
    val subscriptionArtistIds: List<SubscriptionArtistId>
)