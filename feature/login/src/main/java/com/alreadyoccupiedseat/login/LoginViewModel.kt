package com.alreadyoccupiedseat.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface LoginScreenEvent {
    data object Idle : LoginScreenEvent
    data object LoginRequested : LoginScreenEvent
    data object LoginCompleted : LoginScreenEvent
    data class LoginError(val errorMessage: String) : LoginScreenEvent
}

class LoginViewModel @Inject constructor() : ViewModel() {

    private var _event = MutableStateFlow<LoginScreenEvent>(LoginScreenEvent.Idle)
    val event = _event

    fun loginCompleted() {
        viewModelScope.launch {
            // TODO Login
            _event.emit(LoginScreenEvent.LoginCompleted)
        }
    }

}