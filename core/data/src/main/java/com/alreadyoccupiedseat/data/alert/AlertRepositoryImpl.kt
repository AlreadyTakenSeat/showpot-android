package com.alreadyoccupiedseat.data.alert

import com.alreadyoccupiedseat.model.Alert
import javax.inject.Inject

class AlertRepositoryImpl @Inject constructor(
    private val alertDataSource: AlertDataSource
) : AlertRepository {

    override suspend fun getAlerts(cursorId: String?, size: Int): Result<List<Alert>> {
        return alertDataSource.getAlerts(cursorId, size)
    }

    override suspend fun getAlertsExist(): Boolean {
        return alertDataSource.getAlertsExist()
    }
}