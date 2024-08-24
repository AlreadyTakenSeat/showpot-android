package com.alreadyoccupiedseat.data.artist

import com.alreadyoccupiedseat.model.Artist

interface ArtistDataSource {

    suspend fun searchArtists(
        sortedStandard: String? = null,
        cursor: String? = null,
        size: Int,
        search: String,
    ): List<Artist>

    suspend fun getUnsubscribedArtists(
        sortedStandard: String? = null,
        artistGenderApiTypes: List<String>? = null,
        artistApiTypes: List<String>? = null,
        genreIds: List<String>? = null,
        cursor: String? = null,
        size: Int,
    ): List<Artist>

    suspend fun subscribeArtists(
        artistIds: List<String>,
    ): List<String>
}