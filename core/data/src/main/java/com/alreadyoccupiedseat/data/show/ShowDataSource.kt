package com.alreadyoccupiedseat.data.show

import com.alreadyoccupiedseat.model.show.ShowDetail

interface ShowDataSource {

    suspend fun getShowDetail(showId: String): ShowDetail
}