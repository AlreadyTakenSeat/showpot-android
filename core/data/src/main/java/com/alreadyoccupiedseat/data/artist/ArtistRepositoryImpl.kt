package com.alreadyoccupiedseat.data.artist

import com.alreadyoccupiedseat.model.Artist
import com.alreadyoccupiedseat.model.SubscribedArtist
import com.alreadyoccupiedseat.model.artist.SubscriptionArtistId
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    private val artistDataSource: ArtistDataSource
) : ArtistRepository {

    override suspend fun searchArtists(
        cursorId: Int?,
        size: Int,
        search: String,
    ): Result<List<SubscribedArtist>> {
        return artistDataSource.searchArtists(
            cursorId,
            size,
            search,
        )
    }

    override suspend fun getUnsubscribedArtists(
        sortedStandard: String?,
        artistGenderApiTypes: List<String>?,
        artistApiTypes: List<String>?,
        genreIds: List<String>?,
        cursorId: Int?,
        size: Int,
    ): List<Artist> {
        return artistDataSource.getUnsubscribedArtists(
            sortedStandard,
            artistGenderApiTypes,
            artistApiTypes,
            genreIds,
            cursorId,
            size,
        )
    }

    override suspend fun getSubscribedArtists(
        sort: String?,
        cursorId: Int?,
        size: Int
    ): List<Artist> {
        return artistDataSource.getSubscribedArtists(
            sort,
            cursorId,
            size,
        )
    }

    override suspend fun subscribeArtists(artistIds: List<String>): List<SubscriptionArtistId> {
        return artistDataSource.subscribeArtists(artistIds)
    }

    override suspend fun unSubscribeArtists(artistIds: List<String>): List<String> {
        return artistDataSource.unSubscribeArtists(artistIds)
    }
}