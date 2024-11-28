package com.alreadyoccupiedseat.data.artist

import com.alreadyoccupiedseat.model.Artist
import com.alreadyoccupiedseat.model.SubscribedArtist
import com.alreadyoccupiedseat.model.artist.SubscriptionArtistId

interface ArtistRepository {

    suspend fun searchArtists(
        cursorId: Int? = null,
        size: Int,
        search: String,
    ): Result<List<SubscribedArtist>>

    suspend fun getUnsubscribedArtists(
        sortedStandard: String? = null,
        artistGenderApiTypes: List<String>? = null,
        artistApiTypes: List<String>? = null,
        genreIds: List<String>? = null,
        cursorId: Int? = null,
        size: Int,
    ): List<Artist>

    suspend fun getSubscribedArtists(
        sort: String? = null,
        cursorId: Int? = null,
        size: Int,
    ): List<Artist>

    suspend fun subscribeArtists(
        artistIds: List<String>,
    ): List<SubscriptionArtistId>

    suspend fun unSubscribeArtists(
        artistIds: List<String>,
    ): List<String>
}