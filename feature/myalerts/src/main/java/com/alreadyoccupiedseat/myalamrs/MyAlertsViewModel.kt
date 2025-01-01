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
    val isLoading: Boolean = false,
    val isExist: Boolean = false,
    val alerts: List<Alert> = emptyList(),
    val cursorId: String? = null,
    val hasNext: Boolean = false
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

    private fun setIsLoading(isLoading: Boolean) = intent {
        reduce {
            state.copy(isLoading = isLoading)
        }
    }

    private fun getAlerts() = intent {
        setIsLoading(isLoading = true)
        val result = alertRepository.getAlerts(null, 30)
        setIsLoading(isLoading = false)
        result.onSuccess {
            reduce {
                state.copy(
                    alerts = it.data,
                    cursorId = it.cursor.id,
                    hasNext = it.hasNext
                )
            }
        }.onFailure {
            errorLog(it.toApiErrorResult())
        }
    }

    fun loadNextPage() = intent {

        if (!state.hasNext) return@intent

        val result = alertRepository.getAlerts(state.cursorId, 30)

        result.onSuccess {
            reduce {
                state.copy(
                    alerts = state.alerts + it.data,
                    cursorId = it.cursor.id,
                    hasNext = it.hasNext
                )
            }
        }.onFailure {
            errorLog(it.toApiErrorResult())
        }
    }
}
