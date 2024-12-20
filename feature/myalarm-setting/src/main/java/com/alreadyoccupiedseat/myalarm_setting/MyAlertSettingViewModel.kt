package com.alreadyoccupiedseat.myalarm_setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.data.show.ShowRepository
import com.alreadyoccupiedseat.model.show.ShowType
import com.alreadyoccupiedseat.model.temp.AlertReservedShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface MyAlertSettingEvent {
    data object Idle : MyAlertSettingEvent

    data object AlertRegisterSuccess : MyAlertSettingEvent
}
data class MyAlertSettingState(
    val alertReservedShowList: List<AlertReservedShow> = emptyList(),
    val isAlertOptionSheetVisible: Boolean = false,
    val isTicketSheetVisible: Boolean = false,
    val selectedShowId: String? = null,
    val isFirstItemAvailable: Boolean = true,
    val isSecondItemAvailable: Boolean = true,
    val isThirdItemAvailable: Boolean = true,
    val isFirstItemSelected: Boolean = false,
    val isSecondItemSelected: Boolean = false,
    val isThirdItemSelected: Boolean = false,
)

@HiltViewModel
class MyAlertSettingViewModel @Inject constructor(
    private val showRepository: ShowRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MyAlertSettingState())
    val state = _state

    private val _event = MutableSharedFlow<MyAlertSettingEvent>()
    val event = _event

    init {
        getAlertReservedShow()
    }

    fun getAlertReservedShow() {
        viewModelScope.launch {
            showRepository.getAlertReservedShow(
                size = 100,
                type = ShowType.NORMAL.name
            ).let {
                _state.value = _state.value.copy(
                    alertReservedShowList = it
                )
            }
        }
    }

    fun setAlertOptionSheetVisible(isVisible: Boolean) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isAlertOptionSheetVisible = isVisible
            )
        }
    }

    fun setTicketSheetVisible(isVisible: Boolean) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isTicketSheetVisible = isVisible
            )
        }
    }

    fun setSelectedShowId(id: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                selectedShowId = id
            )
        }
    }

    fun changeFirstItemSelection() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isFirstItemSelected = !_state.value.isFirstItemSelected
            )
        }
    }

    fun changeSecondItemSelection() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isSecondItemSelected = !_state.value.isSecondItemSelected
            )
        }
    }

    fun changeThirdItemSelection() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isThirdItemSelected = !_state.value.isThirdItemSelected
            )
        }
    }

    fun registerTicketingAlert(
        ticketingApiType: String,
        alertTimes: List<String>
    ) {
        viewModelScope.launch {
            val showId = _state.value.selectedShowId ?: String()
            val result = showRepository.registerTicketingAlert(
                    showId = showId,
                    ticketingApiType = ticketingApiType,
                    alertTimes = alertTimes
                )
            if (result.isSuccess) {
                _event.emit(MyAlertSettingEvent.AlertRegisterSuccess)
                alertTimes.ifEmpty {
                    removeSelectedShowFromAlert(showId = showId)
                }
            } else {
                println(result.exceptionOrNull())
            }
        }
    }

    fun clearNotification() {
        viewModelScope.launch {
            val showId = _state.value.selectedShowId ?: String()
            val result = showRepository.registerTicketingAlert(
                showId = showId,
                ticketingApiType = ShowType.NORMAL.name,
                alertTimes = emptyList()
            )
            if (result.isSuccess) {
                removeSelectedShowFromAlert(showId = showId)
            } else {
                println("Failed to remove alert")
            }
        }
    }

    private fun removeSelectedShowFromAlert(showId: String) {
        _state.value = _state.value.copy(
            alertReservedShowList = _state.value.alertReservedShowList.filter { it.id != showId },
            isAlertOptionSheetVisible = false,
            selectedShowId = null,
        )
    }

    fun checkAlertAvailability() {
        viewModelScope.launch {
            val availabilitiesInfo = showRepository.checkAlertReservation(state.value.selectedShowId ?: String(), ShowType.NORMAL.name)
            _state.value = _state.value.copy(
                isFirstItemSelected = availabilitiesInfo.alertReservationStatus.before24,
                isSecondItemSelected = availabilitiesInfo.alertReservationStatus.before6,
                isThirdItemSelected = availabilitiesInfo.alertReservationStatus.before1,
                isFirstItemAvailable = availabilitiesInfo.alertReservationAvailability.canReserve24,
                isSecondItemAvailable = availabilitiesInfo.alertReservationAvailability.canReserve6,
                isThirdItemAvailable = availabilitiesInfo.alertReservationAvailability.canReserve1
            )
        }
    }

}