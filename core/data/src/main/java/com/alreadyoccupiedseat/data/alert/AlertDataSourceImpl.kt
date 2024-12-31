package com.alreadyoccupiedseat.data.alert

import com.alreadyoccupiedseat.data.getResult
import com.alreadyoccupiedseat.model.Alert
import com.alreadyoccupiedseat.model.PagingData
import com.alreadyoccupiedseat.network.AlertService
import javax.inject.Inject

class AlertDataSourceImpl @Inject constructor(
    private val alertService: AlertService,
) : AlertDataSource {
    override suspend fun getAlerts(cursorId: String?, size: Int): Result<PagingData<Alert>> {
        return runCatching {
            alertService.getAlerts(cursorId, size).getResult {
                it.data
            }
        }
    }

    override suspend fun getAlertsExist(): Boolean {
        return alertService.isExistAlerts().body()?.data?.isExist ?: false
    }
}