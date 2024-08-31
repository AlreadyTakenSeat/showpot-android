package com.alreadyoccupiedseat.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.data.login.LoginRepository
import com.alreadyoccupiedseat.datastore.AccountDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface AccountScreenEvent {
    data object Idle : AccountScreenEvent
    data object AccountLogout : AccountScreenEvent
    data object Withdrawal : AccountScreenEvent

}

data class AccountScreenState(
    val nickName: String = String.EMPTY,
    val isLoggedIn: Boolean = false
)

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountDataStore: AccountDataStore,
    private val loginRepository: LoginRepository
): ViewModel() {

    private val _state = MutableStateFlow<AccountScreenState>(AccountScreenState())
    val state = _state

    private var _event = MutableSharedFlow<AccountScreenEvent>()
    val event = _event

    init {
        viewModelScope.launch {
            getFcmToken()
            viewModelScope.launch {
                accountDataStore.getAccessTokenFlow().collectLatest { token ->
                    _state.value = _state.value.copy(isLoggedIn = token != null)
                }
            }
        }
    }

    private fun getFcmToken() {
        viewModelScope.launch {
            Log.i("AccountViewModel", "getAccessToken: ${accountDataStore.getAccessToken()}")
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
            loginRepository.requestWithDraw().onSuccess {
                _event.emit(AccountScreenEvent.Withdrawal)
            }
        }
    }

    fun getNickName() {
        viewModelScope.launch {
            loginRepository.getProfile().onSuccess {
                _state.value = state.value.copy(nickName = it.nickname)
            }
        }
    }

}