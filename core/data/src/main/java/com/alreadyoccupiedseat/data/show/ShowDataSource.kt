package com.alreadyoccupiedseat.data.show

import com.alreadyoccupiedseat.model.SearchedShow
import com.alreadyoccupiedseat.model.show.Data
import com.alreadyoccupiedseat.model.show.ShowDetail

interface ShowDataSource {

    suspend fun getEntireShow(
        sort: String,
        onlyOpenSchedule: Boolean,
        size: Int
    ): List<Data>

    suspend fun searchShows(
        cursorId: String? = null,
        size: Int,
        search: String
    ): List<SearchedShow>
    suspend fun getShowDetail(showId: String): ShowDetail

}