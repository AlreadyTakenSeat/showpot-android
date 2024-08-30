package com.alreadyoccupiedseat.myalarm_setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.data.show.ShowRepository
import com.alreadyoccupiedseat.model.show.Shows
import com.alreadyoccupiedseat.model.temp.AlarmReservedShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//getAlertReservedShow
data class MyAlarmSettingState(
    val alarmReservedShow: List<AlarmReservedShow> = emptyList(),
    val isAlarmOptionSheetVisible: Boolean = false, // 알람 옵션 시트 표시 상태
    val isTicketSheetVisible: Boolean = false, // 티켓 시트 표시 상태
    val selectedShowId: String? = null, // 선택된 쇼 ID
    val isFirstItemSelected: Boolean = false, // 첫 번째 아이템 선택 상태
    val isSecondItemSelected: Boolean = false, // 두 번째 아이템 선택 상태
    val isThirdItemSelected: Boolean = false, // 세 번째 아이템 선택 상태
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

    private fun getAlarmReservedShow() {
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

    fun setFirstItemSelected(isFirstItemSelected: Boolean) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isFirstItemSelected = isFirstItemSelected
            )
        }
    }

    fun setSecondItemSelected(isSecondItemSelected: Boolean) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isSecondItemSelected = isSecondItemSelected
            )
        }
    }

    fun setThirdItemSelected(isThirdItemSelected: Boolean) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isThirdItemSelected = isThirdItemSelected
            )
        }
    }

    fun removeClicked() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                alarmReservedShow = _state.value.alarmReservedShow.filter { it.id != _state.value.selectedShowId },
                isAlarmOptionSheetVisible = false,
                selectedShowId = null,
            )
        }
    }

}