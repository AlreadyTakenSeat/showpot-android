package com.alreadyoccupiedseat.show_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.data.show.ShowRepository
import com.alreadyoccupiedseat.datastore.AccountDataStore
import com.alreadyoccupiedseat.model.show.ShowDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ShowDetailEvent {
    data object Idle : ShowDetailEvent

    data object AlertRegisterSuccess : ShowDetailEvent
}

data class ShowDetailState(
    val isLoggedIn: Boolean = false,
    val showId: String = "", // TODO: 아마 삭제
    val showDetail: ShowDetail? = null,
    val isAlertSheetVisible: Boolean = false,
    val isLoginSheetVisible: Boolean = false,
    val isFirstItemAvailable: Boolean = false,
    val isSecondItemAvailable: Boolean = false,
    val isThirdItemAvailable: Boolean = false,
    val isFirstItemSelected: Boolean = false,
    val isSecondItemSelected: Boolean = false,
    val isThirdItemSelected: Boolean = false,
)

@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    private val showRepository: ShowRepository,
    private val accountDataStore: AccountDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(ShowDetailState())
    val state = _state

    private val _event = MutableSharedFlow<ShowDetailEvent>()
    val event = _event

    fun registerShowId(showId: String) {
        _state.value = _state.value.copy(showId = showId)
    }

    fun getShowDetail(showId: String) {
        viewModelScope.launch {
            val result = showRepository.getShowDetail(showId)
            _state.value = ShowDetailState(showDetail = result)
        }
    }

    fun registerShowInterest(showId: String) {
        viewModelScope.launch {
            val isInterested = showRepository.registerShowInterest(showId)
            _state.value =
                _state.value.copy(showDetail = _state.value.showDetail?.copy(isInterested = isInterested))
        }
    }

    fun changeAlertSheetVisibility(isVisible: Boolean) {
        _state.value = _state.value.copy(isAlertSheetVisible = isVisible)
    }

    fun changeLoginSheetVisibility(isVisible: Boolean) {
        _state.value = _state.value.copy(isLoginSheetVisible = isVisible)
    }

    fun registerTicketingAlert(
        showId: String,
        ticketingApiType: String,
        alertTimes: List<String>
    ) {
        viewModelScope.launch {
            val result =
                showRepository.registerTicketingAlert(showId, ticketingApiType, alertTimes)
            if (result.isSuccess) {
                _event.emit(ShowDetailEvent.AlertRegisterSuccess)
            } else {
                println(result.exceptionOrNull())
            }
        }
    }

    fun changeFirstItemSelection() {
        _state.value = _state.value.copy(
            isFirstItemSelected =
            !state.value.isFirstItemSelected
        )
    }

    fun changeSecondItemSelection() {
        _state.value = _state.value.copy(
            isSecondItemSelected =
            !state.value.isSecondItemSelected
        )
    }

    fun changeThirdItemSelection() {
        _state.value = _state.value.copy(
            isThirdItemSelected =
            !state.value.isThirdItemSelected
        )
    }

    fun checkLogin() {
        viewModelScope.launch {
            accountDataStore.getAccessTokenFlow().collectLatest {
                delay(100)
                _state.value = _state.value.copy(isLoggedIn = it?.isNotEmpty() ?: false)
            }
        }
    }

    fun checkAlertAvailability(showId: String) {
        viewModelScope.launch {
            val availabilitiesInfo = showRepository.checkAlertReservation(showId, "NORMAL")
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