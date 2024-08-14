package com.alreadyoccupiedseat.showpot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.datastore.AccountDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainActivityState(
    val isOnboardingCompleted: Boolean = false,
    val isLoggedIn: Boolean = false
)

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val accountDataStore: AccountDataStore
): ViewModel() {

    private val _state = MutableStateFlow(MainActivityState())
    val state = _state

    init {
        viewModelScope.launch {
            accountDataStore.getAccessToken()?.let {
                _state.value = state.value.copy(isLoggedIn = true)
            }
        }
    }

    fun onboardingCompleted() {
        _state.value = state.value.copy(isOnboardingCompleted = true)
    }

    fun loginSuccess() {
        _state.value = state.value.copy(isLoggedIn = true)
    }
}