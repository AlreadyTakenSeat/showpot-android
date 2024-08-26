package com.alreadyoccupiedseat.data.genre

import com.alreadyoccupiedseat.model.Genre

interface GenreRepository {
    suspend fun getGenres(
        size: Int,
    ): List<Genre>
}