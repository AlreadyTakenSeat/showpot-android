package com.alreadyoccupiedseat.myalarm_setting

import android.util.Log
import androidx.lifecycle.ViewModel
import com.alreadyoccupiedseat.common.utiils.errorLog
import com.alreadyoccupiedseat.common.utiils.getCurrentDateTime
import com.alreadyoccupiedseat.common.utiils.subtractMinutesFromDateTime
import com.alreadyoccupiedseat.data.show.ShowRepository
import com.alreadyoccupiedseat.data.toApiErrorResult
import com.alreadyoccupiedseat.datastore.AccountDataStore
import com.alreadyoccupiedseat.enum.TicketingAlertTime
import com.alreadyoccupiedseat.model.TicketingBoxSelectionState
import com.alreadyoccupiedseat.model.show.ShowType
import com.alreadyoccupiedseat.model.temp.AlertReservedShow
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

sealed interface MyAlertSettingEvent {
    data object Idle : MyAlertSettingEvent
    data object AlertRegisterSuccess : MyAlertSettingEvent
}

data class MyAlertSettingState(
    val isLoggedIn: Boolean = false,
    val alertReservedShowList: List<AlertReservedShow> = emptyList(),
    val isAlertOptionSheetVisible: Boolean = false,
    val cursorId: String? = null,
    val hasNext: Boolean = false,
    val selectedShowId: String? = null,
    val ticketingBoxSelectionState: List<TicketingBoxSelectionState> =
        TicketingAlertTime
            .entries
            .map {
                TicketingBoxSelectionState(
                    isAvailable = true, isSelected = false,
                    minute = it.minute
                )
            },
    val isAlertSheetVisible: Boolean = false
)

@HiltViewModel
class MyAlertSettingViewModel @Inject constructor(
    private val showRepository: ShowRepository,
    private val accountDataStore: AccountDataStore
) : ViewModel(), ContainerHost<MyAlertSettingState, MyAlertSettingEvent> {

    override val container: Container<MyAlertSettingState, MyAlertSettingEvent> =
        container(MyAlertSettingState())

    init {
        getAlertReservedShow()
        intent {
            accountDataStore.getAccessTokenFlow().collect {
                reduce {
                    state.copy(isLoggedIn = it?.isNotEmpty() ?: false)
                }
            }
        }
    }

    fun getAlertReservedShow() = intent {
        val result = showRepository.getAlertReservedShow(
            cursorId = null,
            type = ShowType.CONTINUE.text,
            size = 30
        )
        result.onSuccess {
            reduce {
                state.copy(
                    alertReservedShowList = it.data,
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
        val result = showRepository.getAlertReservedShow(
            cursorId = state.cursorId,
            type = ShowType.CONTINUE.text,
            size = 30
        )
        result.onSuccess {
            reduce {
                state.copy(
                    alertReservedShowList = state.alertReservedShowList + it.data,
                    cursorId = it.cursor.id,
                    hasNext = it.hasNext
                )
            }
        }.onFailure {
            errorLog(it.toApiErrorResult())
        }
    }

    fun setAlertOptionSheetVisible(isVisible: Boolean) = intent {
        reduce {
            state.copy(isAlertOptionSheetVisible = isVisible)
        }
    }

    fun setAlertSheetVisible(isVisible: Boolean) = intent {
        reduce {
            state.copy(isAlertSheetVisible = isVisible)
        }
    }

    fun setSelectedShowId(id: String) = intent {
        reduce {
            state.copy(selectedShowId = id)
        }
    }

    fun changeTicketingSelectionBoxState(index: Int) = intent {
        reduce {
            state.copy(
                ticketingBoxSelectionState = state.ticketingBoxSelectionState
                    .mapIndexed { i, ticketingBoxSelectionState ->
                        if (i == index) {
                            ticketingBoxSelectionState.copy(isSelected = !ticketingBoxSelectionState.isSelected)
                        } else {
                            ticketingBoxSelectionState
                        }
                    }
            )
        }
    }

    fun registerTicketingAlert(
        ticketingApiType: String = ShowType.NORMAL.text
    ) = intent {
        val ticketTimeAt = state.alertReservedShowList.find { it.id == state.selectedShowId }?.ticketingAt
        val timeList = state.ticketingBoxSelectionState.withIndex()
            .filter { it.value.isSelected }
            .map {
                subtractMinutesFromDateTime(
                    ticketTimeAt ?: getCurrentDateTime(),
                    TicketingAlertTime.entries[it.index].minute.toLong()
                )
            }
        val showId = state.selectedShowId.orEmpty()
        val result = showRepository.registerTicketingAlert(
            showId = showId,
            ticketingApiType = ticketingApiType,
            alertTimes = timeList
        )
        result.onSuccess {
            postSideEffect(MyAlertSettingEvent.AlertRegisterSuccess)
            if (timeList.isEmpty()) {
                removeSelectedShowFromAlert(showId)
            }
        }.onFailure {
            errorLog(it.toApiErrorResult())
        }
    }

    fun clearNotification() = intent {
        val showId = state.selectedShowId.orEmpty()
        val result = showRepository.registerTicketingAlert(
            showId = showId,
            ticketingApiType = ShowType.NORMAL.text,
            alertTimes = emptyList()
        )
        result.onSuccess {
            postSideEffect(MyAlertSettingEvent.AlertRegisterSuccess)
            removeSelectedShowFromAlert(showId)
        }.onFailure {
            errorLog(it.toApiErrorResult())
        }
    }

    private fun removeSelectedShowFromAlert(showId: String) = intent {
        reduce {
            state.copy(
                alertReservedShowList = state.alertReservedShowList.filter { it.id != showId },
                isAlertOptionSheetVisible = false,
                selectedShowId = null
            )
        }
    }

    fun checkAlertReservation(showId: String, ticketingApiType: String) = intent {
        if (showId.isEmpty()) return@intent
        val result = showRepository.checkAlertReservation(showId, ticketingApiType)
        val alreadySelectedTimeList = result.times.map {
            it.beforeMinutes
        }
        reduce {
            state.copy(
                ticketingBoxSelectionState = state.ticketingBoxSelectionState
                    .map { ticketingBoxSelectionState ->
                        if (alreadySelectedTimeList
                                .contains(ticketingBoxSelectionState.minute)
                        ) {
                            ticketingBoxSelectionState.copy(isSelected = true)
                        } else {
                            ticketingBoxSelectionState.copy(isSelected = false)
                        }
                    }
            )
        }
    }

}
