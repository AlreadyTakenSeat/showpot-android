package com.alreadyoccupiedseat.model

data class SubscribedArtist(
    val id: String,
    val imageURL: String,
    val koreanName: String,
    val englishName: String,
    val isSubscribed: Boolean,
)

