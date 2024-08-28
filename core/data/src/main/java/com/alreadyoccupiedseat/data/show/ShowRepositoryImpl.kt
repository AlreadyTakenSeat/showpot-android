package com.alreadyoccupiedseat.data.show

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.alreadyoccupiedseat.model.SearchedShow
import com.alreadyoccupiedseat.model.show.ShowDetail
import com.alreadyoccupiedseat.network.ShowService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class ShowRepositoryImpl @Inject constructor(
    private val showDataSource: ShowDataSource
) : ShowRepository {
    override suspend fun searchShows(
        cursorId: String?,
        size: Int,
        search: String
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