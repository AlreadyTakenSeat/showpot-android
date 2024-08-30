package com.alreadyoccupiedseat.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.datastore.AccountDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NotificationState(
    val isLoggedIn: Boolean = false,
    // TODO: Implement this
    val upcomingTicketingShows: List<Any> = emptyList(),
)

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val accountDataSource: AccountDataStore,
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
}