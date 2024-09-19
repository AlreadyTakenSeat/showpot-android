package com.alreadyoccupiedseat.model.artist

import androidx.annotation.Keep

@Keep
data class UnSubscribeArtistsResponse(
    val successUnsubscriptionArtistIds: List<String>
)