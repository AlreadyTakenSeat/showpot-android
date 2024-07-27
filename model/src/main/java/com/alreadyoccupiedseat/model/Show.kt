package com.alreadyoccupiedseat.model

data class Show(
    val artist: Artist,
    val genre: Genre,
    val id: String,
    val name: String,
    val posterImageURL: String,
    val ticketingAndShowInfo: List<TicketingAndShowInfo>
)