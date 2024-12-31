package com.alreadyoccupiedseat.data.alert

import com.alreadyoccupiedseat.model.Alert
import com.alreadyoccupiedseat.model.PagingData
import javax.inject.Inject

class AlertRepositoryImpl @Inject constructor(
    private val alertDataSource: AlertDataSource
) : AlertRepository {

    override suspend fun getAlerts(cursorId: String?, size: Int): Result<PagingData<Alert>> {
        return alertDataSource.getAlerts(cursorId, size)
    }

    override suspend fun getAlertsExist(): Boolean {
        return alertDataSource.getAlertsExist()
    }
}