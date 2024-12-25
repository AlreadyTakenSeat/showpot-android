package com.alreadyoccupiedseat.show_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.common.utiils.getCurrentDateTime
import com.alreadyoccupiedseat.common.utiils.subtractMinutesFromDateTime
import com.alreadyoccupiedseat.data.show.ShowRepository
import com.alreadyoccupiedseat.datastore.AccountDataStore
import com.alreadyoccupiedseat.enum.TicketingAlertTime
import com.alreadyoccupiedseat.model.TicketingBoxSelectionState
import com.alreadyoccupiedseat.model.show.ShowDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
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
    val ticketingBoxSelectionState: List<TicketingBoxSelectionState> =
        TicketingAlertTime
            .entries
            .map {
                TicketingBoxSelectionState(
                    isAvailable = true, isSelected = false,
                    minute = it.minute
                )
            }
)

@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    private val showRepository: ShowRepository,
    private val accountDataStore: AccountDataStore
) : ViewModel(), ContainerHost<ShowDetailState, ShowDetailEvent> {

    override val container: Container<ShowDetailState, ShowDetailEvent> =
        container(ShowDetailState())

    init {
        intent {
            accountDataStore.getAccessTokenFlow().collect {
                reduce {
                    state.copy(isLoggedIn = it?.isNotEmpty() ?: false)
                }
            }
        }
    }

    fun registerShowId(showId: String) = intent {
        reduce {
            state.copy(showId = showId)
        }
    }

    fun getShowDetail(showId: String) = intent {
        val result = showRepository.getShowDetail(showId)
        reduce {
            state.copy(showDetail = result)
        }
    }

    fun registerShowInterest(showId: String) = intent {
        val isInterested = showRepository.registerShowInterest(showId)
        reduce {
            state.copy(showDetail = state.showDetail?.copy(isInterested = isInterested))
        }
    }

    fun changeAlertSheetVisibility(isVisible: Boolean) = intent {
        reduce {
            state.copy(isAlertSheetVisible = isVisible)
        }
    }

    fun changeLoginSheetVisibility(isVisible: Boolean) = intent {
        reduce {
            state.copy(isLoginSheetVisible = isVisible)
        }
    }

    fun registerTicketingAlert(
        ticketingApiType: String,
    ) = intent {

        val timeList = state.ticketingBoxSelectionState.withIndex()
            .filter { it.value.isSelected }
            .map {
                subtractMinutesFromDateTime(
                    // TODO: 여러 티켓팅 시간이 있을 경우 대응
                    state.showDetail?.ticketingTimes?.first()?.ticketingAt ?: getCurrentDateTime(),
                    TicketingAlertTime.entries[it.index].minute.toLong()
                )
            }

        if (timeList.isNotEmpty()) {
            val result =
                showRepository.registerTicketingAlert(
                    state.showId, ticketingApiType,
                    timeList
                )

            result.onSuccess {
                postSideEffect(ShowDetailEvent.AlertRegisterSuccess)
            }.onFailure {
                println(result.exceptionOrNull())
            }
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

    fun checkAlertReservation(showId: String, ticketingApiType: String) = intent {

        val result = showRepository.checkAlertReservation(showId, ticketingApiType)
        // TODO: 고도화?
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
                            ticketingBoxSelectionState
                        }
                    }
            )
        }
    }

}