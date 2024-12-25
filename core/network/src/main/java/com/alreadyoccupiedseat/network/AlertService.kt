package com.alreadyoccupiedseat.network

import com.alreadyoccupiedseat.model.Alert
import com.alreadyoccupiedseat.model.AlertExist
import com.alreadyoccupiedseat.model.ApiResult
import com.alreadyoccupiedseat.model.PagingData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlertService {

    @GET("/api/v1/users/notifications")
    suspend fun getAlerts(
        @Query("cursorId") cursorId: String?,
        @Query("size") size: Int
    ): Response<ApiResult<PagingData<Alert>>>

    @GET("/api/v1/users/notifications/exist")
    suspend fun isExistAlerts(): Response<ApiResult<AlertExist>>

}