package com.alreadyoccupiedseat.data.show

import com.alreadyoccupiedseat.model.SearchedShow
import com.alreadyoccupiedseat.model.show.Data
import com.alreadyoccupiedseat.model.show.InterestedData
import com.alreadyoccupiedseat.model.show.ShowDetail
import com.alreadyoccupiedseat.model.temp.AlarmReservedShow
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

    override suspend fun getInterestedShowList(size: Int): List<InterestedData> {
        return showDataSource.getInterestedShowList(size)
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

    override suspend fun registerShowInterest(showId: String): Boolean {
        return showDataSource.registerShowInterest(showId)
    }

    /** 알림 설정한 공연 목록 조회 ***/
    override suspend fun getAlarmReservedShow(size: Int, type: String): List<AlarmReservedShow> {
        return showDataSource.getAlarmReservedShow(size, type)
    }

}