package com.alreadyoccupiedseat.data.artist

import com.alreadyoccupiedseat.data.getResult
import com.alreadyoccupiedseat.model.Artist
import com.alreadyoccupiedseat.model.SearchedArtist
import com.alreadyoccupiedseat.model.artist.SubscribeArtistsRequest
import com.alreadyoccupiedseat.model.artist.SubscriptionArtistId
import com.alreadyoccupiedseat.model.artist.UnSubscribeArtistsRequest
import com.alreadyoccupiedseat.network.ArtistService
import javax.inject.Inject

// Todo: Error Handling
class ArtistDataSourceImpl @Inject constructor(
    private val artistService: ArtistService
) : ArtistDataSource {
    override suspend fun searchArtists(
        cursorId: Int?,
        size: Int,
        search: String,
    ): Result<List<SearchedArtist>> {

        return runCatching {
            artistService.searchArtists(
                cursorId,
                size,
                search,
            ).getResult {
                it.data.data
            }
        }
    }

    override suspend fun getUnsubscribedArtists(
        sortedStandard: String?,
        artistGenderApiTypes: List<String>?,
        artistApiTypes: List<String>?,
        genreIds: List<String>?,
        cursorId: Int?,
        size: Int,
    ): List<Artist> {
        return artistService.getUnsubscribedArtists(
            sortedStandard,
            artistGenderApiTypes,
            artistApiTypes,
            genreIds,
            cursorId,
            size,
        ).body()?.data?.data ?: emptyList()
    }

    override suspend fun getSubscribedArtists(
        sort: String?,
        cursorId: Int?,
        size: Int
    ): List<Artist> {
        return artistService.getSubscribedArtists(
            sort,
            cursorId,
            size,
        ).body()?.data?.data ?: emptyList()
    }

    override suspend fun subscribeArtists(artistIds: List<String>): List<SubscriptionArtistId> {
        return artistService.subscribeArtists(SubscribeArtistsRequest(artistIds))
            .body()?.data?.subscriptionArtistIds ?: emptyList()
    }

    override suspend fun unSubscribeArtists(artistIds: List<String>): List<String> {
        return artistService.unSubscribeArtists(UnSubscribeArtistsRequest(artistIds))
            .body()?.data?.successUnsubscriptionArtistIds ?: emptyList()
    }
}