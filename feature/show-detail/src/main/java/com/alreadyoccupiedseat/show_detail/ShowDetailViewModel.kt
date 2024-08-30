package com.alreadyoccupiedseat.show_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.data.show.ShowRepository
import com.alreadyoccupiedseat.model.show.ShowDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ShowDetailEvent {
    data object Idle : ShowDetailEvent

    data object AlertRegisterSuccess : ShowDetailEvent
}

data class ShowDetailState(
    val showDetail: ShowDetail? = null,
    val isSheetVisible: Boolean = false,
    val isFirstItemAvailable: Boolean = false,
    val isSecondItemAvailable: Boolean = false,
    val isThirdItemAvailable: Boolean = true,
    val isFirstItemSelected: Boolean = false,
    val isSecondItemSelected: Boolean = false,
    val isThirdItemSelected: Boolean = false,
)

@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    private val showRepository: ShowRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ShowDetailState())
    val state = _state

    private val _event = MutableStateFlow<ShowDetailEvent>(ShowDetailEvent.Idle)
    val event = _event

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

    fun changeSheetVisibility(isVisible: Boolean) {
        _state.value = _state.value.copy(isSheetVisible = isVisible)
    }

    fun registerTicketingAlert(showId: String, ticketingApiType: String, alertTimes: List<String>) {
        viewModelScope.launch {
            val result = showRepository.registerTicketingAlert(showId, ticketingApiType, alertTimes)
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

    // TODO: generate fun to change the avialability of the first, second, and third item
}