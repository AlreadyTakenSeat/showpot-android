package com.alreadyoccupiedseat.data.show

import com.alreadyoccupiedseat.model.SearchedShow
import com.alreadyoccupiedseat.model.show.Data
import com.alreadyoccupiedseat.model.show.ShowDetail
import javax.inject.Inject


class ShowRepositoryImpl @Inject constructor(
    private val showDataSource: ShowDataSource,
) : ShowRepository {

    override suspend fun getEntireShow(
        sort: String,
        onlyOpenSchedule: Boolean,
        size: Int,
    ): List<Data> {
        return showDataSource.getEntireShow(
            sort = sort,
            onlyOpenSchedule = onlyOpenSchedule,
            size = size
        )
    }

    override suspend fun searchShows(
        cursorId: String?,
        size: Int,
        search: String,
    ): List<SearchedShow> {
        return showDataSource.searchShows(
            cursorId = cursorId,
            size = size,
            search = search,
        )
    }


    override suspend fun getShowDetail(showId: String): ShowDetail {
        return showDataSource.getShowDetail(showId)
    }

}