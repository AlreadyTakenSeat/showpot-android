package com.alreadyoccupiedseat.data.artist

import com.alreadyoccupiedseat.model.Artist
import com.alreadyoccupiedseat.model.PagingData
import com.alreadyoccupiedseat.model.SearchedArtist
import com.alreadyoccupiedseat.model.artist.SubscriptionArtistId
import com.alreadyoccupiedseat.model.artist.UnSubscribedArtist

interface ArtistDataSource {

    suspend fun searchArtists(
        cursorId: Int?,
        size: Int,
        search: String,
    ): Result<List<SearchedArtist>>

    suspend fun getUnsubscribedArtists(
        sortedStandard: String? = null,
        artistGenderApiTypes: List<String>? = null,
        artistApiTypes: List<String>? = null,
        genreIds: List<String>? = null,
        cursorId: String?,
        size: Int,
    ): Result<PagingData<UnSubscribedArtist>>

    suspend fun getSubscribedArtists(
        sort: String? = null,
        cursorId: Int?,
        size: Int,
    ): List<Artist>

    suspend fun subscribeArtists(
        artistIds: List<String>,
    ): Result<List<SubscriptionArtistId>>

    suspend fun unSubscribeArtists(
        artistIds: List<String>,
    ): List<String>
}