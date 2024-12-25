package com.alreadyoccupiedseat.data.alert

import android.content.Context
import com.alreadyoccupiedseat.model.Alert
import com.alreadyoccupiedseat.network.AlertService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AlertDataSourceImpl @Inject constructor(
private val alertService: AlertService,
@ApplicationContext private val context: Context
) : AlertDataSource {
    override suspend fun getAlerts(cursorId: String?, size: Int): List<Alert> {
        return alertService.getAlerts(cursorId, size).body()?.data?.data ?: emptyList()
    }

    override suspend fun getAlertsExist(): Boolean {
        return alertService.isExistAlerts().body()?.data?.isExist ?: false
    }
}