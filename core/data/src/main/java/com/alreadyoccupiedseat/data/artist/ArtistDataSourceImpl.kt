package com.alreadyoccupiedseat.data.artist

import com.alreadyoccupiedseat.model.Artist
import com.alreadyoccupiedseat.network.ArtistService
import javax.inject.Inject

// Todo: Error Handling
class ArtistDataSourceImpl @Inject constructor(
    private val artistService: ArtistService
) : ArtistDataSource {
    override suspend fun searchArtists(
        sortedStandard: String?,
        cursor: String?,
        size: Int,
        search: String,
    ): List<Artist> {
        return artistService.searchArtists(
            sortedStandard,
            cursor,
            size,
            search,
        ).body()?.data ?: emptyList()
    }

    override suspend fun getUnsubscribedArtists(
        sortedStandard: String?,
        artistGenderApiTypes: List<String>?,
        artistApiTypes: List<String>?,
        genreIds: List<String>?,
        cursor: String?,
        size: Int,
    ): List<Artist> {
        return artistService.getUnsubscribedArtists(
            sortedStandard,
            artistGenderApiTypes,
            artistApiTypes,
            genreIds,
            cursor,
            size,
        ).body()?.data ?: emptyList()
    }
}