package com.alreadyoccupiedseat.model.artist

import androidx.annotation.Keep

@Keep
data class SubscribeArtistsRequest(
    val artistIds: List<String>
)