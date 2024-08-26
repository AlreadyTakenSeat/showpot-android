package com.alreadyoccupiedseat.network

import com.alreadyoccupiedseat.model.Artist
import com.alreadyoccupiedseat.model.Genre
import com.alreadyoccupiedseat.model.PagingData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreService {
    @GET("/api/v1/genres")
    suspend fun getGenres(
        @Query("size") size: Int = 20
    ): Response<PagingData<Genre>>

}