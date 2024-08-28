package com.alreadyoccupiedseat.data.genre

import com.alreadyoccupiedseat.model.Genre

interface GenreDataSource {

    suspend fun getGenres(
        size: Int,
    ): List<Genre>

    suspend fun unsubscribeGenres(
        genreIds: List<String>,
    ): List<String>

    suspend fun subscribeGenres(
        genreIds: List<String>,
    ): List<String>

}