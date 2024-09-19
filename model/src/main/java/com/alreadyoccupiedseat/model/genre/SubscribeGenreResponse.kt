package com.alreadyoccupiedseat.model.genre

import androidx.annotation.Keep

@Keep
data class SubscribeGenreResponse(
    val successSubscriptionGenreIds: List<String>
)
