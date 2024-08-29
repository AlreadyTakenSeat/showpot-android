package com.alreadyoccupiedseat.model.show

data class Data(
    val id: String,
    val isOpen: Boolean,
    val location: String,
    val posterImageURL: String,
    val ticketingAt: String,
    val title: String
)