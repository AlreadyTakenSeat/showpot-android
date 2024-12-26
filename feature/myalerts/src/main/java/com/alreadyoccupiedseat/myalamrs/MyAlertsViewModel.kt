package com.alreadyoccupiedseat.myalamrs

import androidx.lifecycle.ViewModel
import com.alreadyoccupiedseat.common.utiils.errorLog
import com.alreadyoccupiedseat.data.alert.AlertRepository
import com.alreadyoccupiedseat.data.toApiErrorResult
import com.alreadyoccupiedseat.model.Alert
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

sealed interface MyAlertsSettingEvent {
    data object Idle : MyAlertsSettingEvent
}

data class MyAlertsState(
    val isExist: Boolean = false,
    val alerts: List<Alert> = emptyList(),
)

@HiltViewModel
class MyAlertsViewModel @Inject constructor(
    private val alertRepository: AlertRepository
) : ViewModel(),
    ContainerHost<MyAlertsState, MyAlertsSettingEvent> {

    override val container: Container<MyAlertsState, MyAlertsSettingEvent> =
        container(MyAlertsState())

    init {
        getAlertsExist()
        getAlerts()
    }

    private fun getAlertsExist() {
        intent {
            val isExist = alertRepository.getAlertsExist()
            reduce {
                state.copy(isExist = isExist)
            }
        }
    }

    private fun getAlerts() {
        intent {
            val alerts = alertRepository.getAlerts(null, 30)
            alerts.onSuccess {
                reduce {
                    state.copy(alerts = it)
                }
            }.onFailure {
                errorLog(it.toApiErrorResult())
            }
        }
    }

}