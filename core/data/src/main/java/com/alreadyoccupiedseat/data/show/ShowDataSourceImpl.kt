package com.alreadyoccupiedseat.data.show

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.alreadyoccupiedseat.model.SearchedShow
import com.alreadyoccupiedseat.model.show.Data
import com.alreadyoccupiedseat.model.show.ShowDetail
import com.alreadyoccupiedseat.network.ShowService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class ShowDataSourceImpl @Inject constructor(
    private val showService: ShowService,
    @ApplicationContext private val context: Context
) : ShowDataSource {

    override suspend fun getEntireShow(
        sort: String,
        onlyOpenSchedule: Boolean,
        size: Int,
    ): List<Data> {
        return showService.getEntireShow(
            sort = sort,
            onlyOpenSchedule = onlyOpenSchedule,
            size = size
        ).body()?.data ?: emptyList()
    }

    override suspend fun searchShows(
        cursorId: String?,
        size: Int,
        search: String
    ): List<SearchedShow> {
        return showService.searchShows(
            cursorId = cursorId,
            size = size,
            search = search,
        ).body()?.data ?: emptyList()
    }

    @SuppressLint("HardwareIds")
    override suspend fun getShowDetail(showId: String): ShowDetail {
        val viewIdentifier =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        return showService.getShowDetail(showId, viewIdentifier).body()
            ?: throw Exception("Show not found")
    }

}