package com.alreadyoccupiedseat.data.alert

import com.alreadyoccupiedseat.model.Alert
import com.alreadyoccupiedseat.network.AlertService
import javax.inject.Inject

class AlertDataSourceImpl @Inject constructor(
private val alertService: AlertService
) : AlertDataSource {
    override suspend fun getAlerts(cursorId: String?, size: Int): List<Alert> {
        return alertService.getAlerts(cursorId, size).body()?.data?.data ?: emptyList()
    }

    override suspend fun getAlertsExist(): Boolean {
        return alertService.isExistAlerts().body()?.data?.isExist ?: false
    }
}