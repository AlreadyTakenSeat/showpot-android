package com.alreadyoccupiedseat.show_detail

import androidx.lifecycle.ViewModel
import com.alreadyoccupiedseat.common.utils.errorLog
import com.alreadyoccupiedseat.common.utils.getCurrentDateTime
import com.alreadyoccupiedseat.common.utils.isDate1GreaterOrEqual
import com.alreadyoccupiedseat.common.utils.subtractMinutesFromDateTime
import com.alreadyoccupiedseat.data.show.ShowRepository
import com.alreadyoccupiedseat.data.toApiErrorResult
import com.alreadyoccupiedseat.datastore.AccountDataStore
import com.alreadyoccupiedseat.enum.TicketingAlertTime
import com.alreadyoccupiedseat.model.TicketingBoxSelectionState
import com.alreadyoccupiedseat.model.show.ShowDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

        result.onSuccess {
            reduce {
                state.copy(showDetail = it)
            }
        }.onFailure {
            errorLog(it.toApiErrorResult().message)
        }
    }

    fun manipulateShowInterest(showId: String) = intent {

        val curShowIsInterested = state.showDetail?.isInterested ?: false
        if (curShowIsInterested) {
            registerShowUnInterest(showId)
        } else {
            registerShowInterest(showId)
        }
    }

    private fun registerShowInterest(showId: String) = intent {
        val result = showRepository.registerShowInterest(showId)

        result.onSuccess {
            reduce {
                state.copy(showDetail = state.showDetail?.copy(isInterested = true))
            }
        }.onFailure {
            errorLog(it.toApiErrorResult().message)
        }

    }

    private fun registerShowUnInterest(showId: String) = intent {
        val result = showRepository.registerShowUnInterest(showId)

        result.onSuccess {
            reduce {
                state.copy(showDetail = state.showDetail?.copy(isInterested = false))
            }
        }.onFailure {
            errorLog(it.toApiErrorResult().message)
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

    fun checkIsAvailableAlertReservation() = intent {

        delay(500)
        val showTicketingDate = state.showDetail?.ticketingTimes?.first()?.ticketingAt ?: return@intent

        reduce {
            state.copy(
                ticketingBoxSelectionState = state.ticketingBoxSelectionState.map { ticketingBoxSelectionState ->

                    val targetDate = subtractMinutesFromDateTime(showTicketingDate, ticketingBoxSelectionState.minute.toLong())

                    if (isDate1GreaterOrEqual(getCurrentDateTime(), targetDate)) {
                        ticketingBoxSelectionState.copy(isAvailable = false)
                    } else {
                        ticketingBoxSelectionState.copy(isAvailable = true)
                    }

                }
            )
        }
    }

}