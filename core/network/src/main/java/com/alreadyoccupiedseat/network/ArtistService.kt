package com.alreadyoccupiedseat.network

import com.alreadyoccupiedseat.model.Artist
import com.alreadyoccupiedseat.model.PagingData
import com.alreadyoccupiedseat.model.SubscribedArtist
import com.alreadyoccupiedseat.model.artist.SubscribeArtistsRequest
import com.alreadyoccupiedseat.model.artist.SubscribeArtistsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ArtistService {

    @GET("api/v1/artists/search")
    suspend fun searchArtists(
        @Query("sortedStandard") sortedStandard: String? = null,
        @Query("cursor") cursor: String? = null,
        @Query("size") size: Int,
        @Query("search") search: String,
    ): Response<PagingData<SubscribedArtist>>

    @GET("api/v1/artists/unsubscriptions")
    suspend fun getUnsubscribedArtists(
        @Query("sortedStandard") sortedStandard: String? = null,
        @Query("artistGenderApiTypes") artistGenderApiTypes: List<String>? = null,
        @Query("artistApiTypes") artistApiTypes: List<String>? = null,
        @Query("genreIds") genreIds: List<String>? = null,
        @Query("cursor") cursor: String? = null,
        @Query("size") size: Int,
    ): Response<PagingData<Artist>>

    // TODO: Make sort to enum class
    @GET("api/v1/artists/subscriptions")
    suspend fun getSubscribedArtists(
        @Query("sort") sort: String? = null,
        @Query("cursor") cursor: String? = null,
        @Query("size") size: Int,
    ): Response<PagingData<Artist>>


    @POST("api/v1/artists/subscribe")
    suspend fun subscribeArtists(
        @Body artistIds: SubscribeArtistsRequest,
    ): Response<SubscribeArtistsResponse>
}