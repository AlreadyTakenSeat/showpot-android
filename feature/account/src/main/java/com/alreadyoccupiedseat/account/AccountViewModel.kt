package com.alreadyoccupiedseat.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.datastore.AccountDataStore
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
class AccountViewModel @Inject constructor(
    private val accountDataStore: AccountDataStore,
): ViewModel() {

    private var _event = MutableSharedFlow<AccountScreenEvent>()
    val event = _event

    init {
        viewModelScope.launch {
            getFcmToken()
        }
    }

    private fun getFcmToken() {
        viewModelScope.launch {
            Log.i("AccountViewModel", "getFcmToken: ${accountDataStore.getFcmToken()}")
        }
    }

    fun clear() {
        viewModelScope.launch {
            accountDataStore.clearAccessToken()
        }
    }
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