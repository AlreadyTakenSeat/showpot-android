package com.alreadyoccupiedseat.data.artist

import com.alreadyoccupiedseat.model.Artist

interface ArtistRepository {

    suspend fun searchArtists(
        sortedStandard: String? = null,
        cursor: String? = null,
        size: Int,
        search: String,
    ): List<Artist>
}