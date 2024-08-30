package com.alreadyoccupiedseat.myalarm_setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.data.show.ShowRepository
import com.alreadyoccupiedseat.enum.TicketingAlertTime
import com.alreadyoccupiedseat.model.show.Shows
import com.alreadyoccupiedseat.model.show.Shows.Companion.NORMAL
import com.alreadyoccupiedseat.model.temp.AlarmReservedShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//getAlertReservedShow
data class MyAlarmSettingState(
    val alarmReservedShow: List<AlarmReservedShow> = emptyList(),
    val isAlarmOptionSheetVisible: Boolean = false,
    val isTicketSheetVisible: Boolean = false,
    val selectedShowId: String? = null,
    val isFirstItemAvailable: Boolean = false,
    val isSecondItemAvailable: Boolean = false,
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

    fun registerTicketingAlert() {
        viewModelScope.launch {
            val showId = _state.value.selectedShowId ?: String()
            val alertTimes = if (state.value.isThirdItemSelected) {
                listOf(TicketingAlertTime.BEFORE_1.name)
            } else {
                emptyList()
            }
            alertTimes.ifEmpty {
                _state.value = _state.value.copy(
                    alarmReservedShow = _state.value.alarmReservedShow.filter { it.id != showId }
                )
            }
            val result = showRepository.registerTicketingAlert(showId, NORMAL, alertTimes)
            if (result.isSuccess) {
                println("Alert registered successfully")
            } else {
                println(result.exceptionOrNull())
            }
        }
    }

    fun removeClicked() {
        viewModelScope.launch {
            val showId = _state.value.selectedShowId ?: String()
            val isSuccess = showRepository.registerTicketingAlert(
                showId = showId,
                ticketingApiType = NORMAL,
                alertTimes = emptyList()
            ).isSuccess
            if (isSuccess) {
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

}