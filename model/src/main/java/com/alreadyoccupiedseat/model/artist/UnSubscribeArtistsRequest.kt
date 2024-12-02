package com.alreadyoccupiedseat.model.artist

import androidx.annotation.Keep

@Keep
data class UnSubscribeArtistsRequest(
    val artistIds: List<String>
)
