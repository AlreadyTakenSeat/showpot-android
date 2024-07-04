package com.alreadyoccupiedseat.showpot

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

data class MainActivityState(
    val isOnboardingCompleted: Boolean = false
)

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(MainActivityState())
    val state = _state

    fun onboardingCompleted() {
        _state.value = state.value.copy(isOnboardingCompleted = true)
    }
}