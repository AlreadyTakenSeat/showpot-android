package com.alreadyoccupiedseat.myalarm_setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.data.show.ShowRepository
import com.alreadyoccupiedseat.model.show.Shows
import com.alreadyoccupiedseat.model.show.Shows.Companion.NORMAL
import com.alreadyoccupiedseat.model.temp.AlarmReservedShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface MyAlarmSettingEvent {
    data object Idle : MyAlarmSettingEvent

    data object AlertRegisterSuccess : MyAlarmSettingEvent
}
data class MyAlarmSettingState(
    val alarmReservedShow: List<AlarmReservedShow> = emptyList(),
    val isAlarmOptionSheetVisible: Boolean = false,
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
class MyAlarmSettingViewModel @Inject constructor(
    private val showRepository: ShowRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MyAlarmSettingState())
    val state = _state

    private val _event = MutableSharedFlow<MyAlarmSettingEvent>()
    val event = _event

    init {
        getAlarmReservedShow()
    }

    fun getAlarmReservedShow() {
        viewModelScope.launch {
            showRepository.getAlarmReservedShow(
                size = 100,
                type = Shows.CONTINUE
            ).let {
                _state.value = _state.value.copy(
                    alarmReservedShow = it
                )
            }
        }
    }

    fun setAlarmOptionSheetVisible(isVisible: Boolean) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isAlarmOptionSheetVisible = isVisible
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
                _event.emit(MyAlarmSettingEvent.AlertRegisterSuccess)
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
                ticketingApiType = NORMAL,
                alertTimes = emptyList()
            )
            if (result.isSuccess) {
                _state.value = _state.value.copy(
                    alarmReservedShow = _state.value.alarmReservedShow.filter { it.id != showId },
                    isAlarmOptionSheetVisible = false,
                    selectedShowId = null,
                )
            } else {
                println("Failed to remove alert")
            }
        }
    }

    fun checkAlertAvailability() {
        viewModelScope.launch {
            val availabilitiesInfo = showRepository.checkAlertReservation(state.value.selectedShowId ?: String(), NORMAL)
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