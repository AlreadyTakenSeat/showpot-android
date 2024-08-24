package com.alreadyoccupiedseat.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface AccountScreenEvent {
    data object Idle : AccountScreenEvent
    data object AccountLogout : AccountScreenEvent
    data object Withdrawal : AccountScreenEvent

}

@HiltViewModel
class AccountViewModel @Inject constructor(): ViewModel() {

    private var _event = MutableSharedFlow<AccountScreenEvent>()
    val event = _event

    fun logout() {
        viewModelScope.launch {
            _event.emit(AccountScreenEvent.AccountLogout)
        }
    }

    fun withdrawal() {
        viewModelScope.launch {
            _event.emit(AccountScreenEvent.Withdrawal)
        }
    }

}