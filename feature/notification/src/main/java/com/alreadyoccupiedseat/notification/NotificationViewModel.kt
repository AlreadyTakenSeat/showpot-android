package com.alreadyoccupiedseat.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.data.show.ShowRepository
import com.alreadyoccupiedseat.datastore.AccountDataStore
import com.alreadyoccupiedseat.model.temp.AlertReservedShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NotificationState(
    val isLoggedIn: Boolean = false,
    val upcomingTicketingShows: List<AlertReservedShow> = emptyList(),
)

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val accountDataSource: AccountDataStore,
    private val showRepository: ShowRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NotificationState())
    val state = _state

    init {
        viewModelScope.launch {
            accountDataSource.getAccessTokenFlow().collect {
                _state.value = _state.value.copy(isLoggedIn = it?.isNotEmpty() ?: false)
            }
        }
    }

    fun getUpcomingTicketingShows() {
        viewModelScope.launch {
            _state.value = _state.value.copy(upcomingTicketingShows = emptyList())
            val upcomingTicketingShows = showRepository.getAlertReservedShow(100, "CONTINUED")
            _state.value = _state.value.copy(upcomingTicketingShows = upcomingTicketingShows)
        }
    }
}