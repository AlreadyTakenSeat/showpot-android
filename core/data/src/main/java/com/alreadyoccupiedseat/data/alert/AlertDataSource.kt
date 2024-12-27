package com.alreadyoccupiedseat.data.alert

import com.alreadyoccupiedseat.model.Alert

interface AlertDataSource {

    suspend fun getAlerts(cursorId: String?, size: Int,): Result<List<Alert>>

    suspend fun getAlertsExist(): Boolean

}