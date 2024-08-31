package com.alreadyoccupiedseat.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.data.login.LoginRepository
import com.alreadyoccupiedseat.datastore.AccountDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface MyPageScreenEvent {
    data object Idle : MyPageScreenEvent
    data object LoginComplete : MyPageScreenEvent
}

data class MyPageScreenState(
    val nickName: String = String.EMPTY,
)

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val accountDataStore: AccountDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(MyPageScreenState())
    val state: StateFlow<MyPageScreenState> = _state

    fun getNickName() {
        viewModelScope.launch {
            if (accountDataStore.getAccessToken().isNullOrEmpty().not()) {
                loginRepository.getProfile().onSuccess { profile ->
                    _state.value = state.value.copy(nickName = profile.nickname)
                }
            } else {
                _state.value = state.value.copy(nickName = String.EMPTY)
            }
        }
    }

}