package com.alreadyoccupiedseat.data.artist

import com.alreadyoccupiedseat.model.Artist
import com.alreadyoccupiedseat.model.SubscribedArtist
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    private val artistDataSource: ArtistDataSource
) : ArtistRepository {

    override suspend fun searchArtists(
        sortedStandard: String?,
        cursor: String?,
        size: Int,
        search: String,
    ): List<SubscribedArtist> {
        return artistDataSource.searchArtists(
            sortedStandard,
            cursor,
            size,
            search,
        )
    }

    override suspend fun getUnsubscribedArtists(
        sortedStandard: String?,
        artistGenderApiTypes: List<String>?,
        artistApiTypes: List<String>?,
        genreIds: List<String>?,
        cursor: String?,
        size: Int,
    ): List<Artist> {
        return artistDataSource.getUnsubscribedArtists(
            sortedStandard,
            artistGenderApiTypes,
            artistApiTypes,
            genreIds,
            cursor,
            size,
        )
    }

    override suspend fun getSubscribedArtists(
        sort: String?,
        cursor: String?,
        size: Int
    ): List<Artist> {
        return artistDataSource.getSubscribedArtists(
            sort,
            cursor,
            size,
        )
    }

    override suspend fun subscribeArtists(artistIds: List<String>): List<String> {
        return artistDataSource.subscribeArtists(artistIds)
    }
}