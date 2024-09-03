package com.alreadyoccupiedseat.showpot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.datastore.AccountDataStore
import com.alreadyoccupiedseat.datastore.OnboardingDataStore
import com.alreadyoccupiedseat.usecase.ReIssueTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface OnboardingCheckState {

    data object OnBoardingInit : OnboardingCheckState
    data object OnBoardingDone : OnboardingCheckState
    data object OnBoardingChecking : OnboardingCheckState
    data object OnBoardingIsNotDone : OnboardingCheckState

}

data class MainActivityState(
    val isOnboardingCompleted: OnboardingCheckState = OnboardingCheckState.OnBoardingInit,
)

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val onboardingDataStore: OnboardingDataStore,
) : ViewModel() {

    private val _state = MutableStateFlow(MainActivityState())
    val state = _state

    init {

        // 온보딩 확인
        viewModelScope.launch {

            _state.value =
                state.value.copy(isOnboardingCompleted = OnboardingCheckState.OnBoardingChecking)

            onboardingDataStore.getWhetherIsFinished().let { isDone ->
                if (isDone) {
                    _state.value =
                        state.value.copy(isOnboardingCompleted = OnboardingCheckState.OnBoardingDone)
                } else {
                    _state.value =
                        state.value.copy(isOnboardingCompleted = OnboardingCheckState.OnBoardingIsNotDone)
                }
            }

        }

    }

    fun onboardingCompleted() {
        _state.value =
            state.value.copy(isOnboardingCompleted = OnboardingCheckState.OnBoardingDone)
    }

}