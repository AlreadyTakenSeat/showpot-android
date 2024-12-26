package com.alreadyoccupiedseat.data.alert

import com.alreadyoccupiedseat.data.getResult
import com.alreadyoccupiedseat.model.Alert
import com.alreadyoccupiedseat.network.AlertService
import javax.inject.Inject

class AlertDataSourceImpl @Inject constructor(
    private val alertService: AlertService,
) : AlertDataSource {
    override suspend fun getAlerts(cursorId: String?, size: Int): Result<List<Alert>> {
        return runCatching {
            alertService.getAlerts(cursorId, size).getResult {
                it.data.data
            }
        }
    }

    override suspend fun getAlertsExist(): Boolean {
        return alertService.isExistAlerts().body()?.data?.isExist ?: false
    }
}