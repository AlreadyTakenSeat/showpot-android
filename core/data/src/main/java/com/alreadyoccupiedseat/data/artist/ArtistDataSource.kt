package com.alreadyoccupiedseat.data.artist

import com.alreadyoccupiedseat.model.Artist
import com.alreadyoccupiedseat.model.SubscribedArtist

interface ArtistDataSource {

    suspend fun searchArtists(
        cursorId: Int?,
        size: Int,
        search: String,
    ): List<SubscribedArtist>

    suspend fun getUnsubscribedArtists(
        sortedStandard: String? = null,
        artistGenderApiTypes: List<String>? = null,
        artistApiTypes: List<String>? = null,
        genreIds: List<String>? = null,
        cursorId: Int?,
        size: Int,
    ): List<Artist>

    suspend fun getSubscribedArtists(
        sort: String? = null,
        cursorId: Int?,
        size: Int,
    ): List<Artist>

    suspend fun subscribeArtists(
        artistIds: List<String>,
    ): List<String>

    suspend fun unSubscribeArtists(
        artistIds: List<String>,
    ): List<String>
}