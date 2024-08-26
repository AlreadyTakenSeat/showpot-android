package com.alreadyoccupiedseat.data.genre

import com.alreadyoccupiedseat.model.Genre

interface GenreDataSource {

    suspend fun getGenres(
        size: Int,
    ): List<Genre>

}