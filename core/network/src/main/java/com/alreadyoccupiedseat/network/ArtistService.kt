package com.alreadyoccupiedseat.network

import com.alreadyoccupiedseat.model.Artist
import com.alreadyoccupiedseat.model.PagingData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistService {

    @GET("api/v1/artists/search")
    suspend fun  searchArtists(
        @Query("sortedStandard") sortedStandard: String? = null,
        @Query("cursor") cursor: String? = null,
        @Query("size") size: Int,
        @Query("search") search: String,
    ): Response<PagingData<Artist>>
}