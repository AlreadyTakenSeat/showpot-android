package com.alreadyoccupiedseat.data.alert

import com.alreadyoccupiedseat.model.Alert
import com.alreadyoccupiedseat.model.PagingData

interface AlertDataSource {

    suspend fun getAlerts(cursorId: String?, size: Int,): Result<PagingData<Alert>>

    suspend fun getAlertsExist(): Boolean

}