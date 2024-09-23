package com.alreadyoccupiedseat.model.genre

import androidx.annotation.Keep

@Keep
data class SubscribeGenreRequest(
    val genreIds: List<String>
)
