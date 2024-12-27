package com.alreadyoccupiedseat.data.alert

import com.alreadyoccupiedseat.model.Alert

interface AlertRepository {

    suspend fun getAlerts(cursorId: String?, size: Int): Result<List<Alert>>

    suspend fun getAlertsExist(): Boolean

}