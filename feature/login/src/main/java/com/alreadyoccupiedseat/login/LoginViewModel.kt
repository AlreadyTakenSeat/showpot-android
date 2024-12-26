package com.alreadyoccupiedseat.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.common.utiils.errorLog
import com.alreadyoccupiedseat.data.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

sealed interface LoginScreenEvent {
    data object Idle : LoginScreenEvent
    data object LoginRequested : LoginScreenEvent
    data object LoginCompleted : LoginScreenEvent
    data class LoginError(val errorMessage: String) : LoginScreenEvent
}

data class LoginScreenState(
    val unit: Unit = Unit
)


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel(), ContainerHost<LoginScreenState, LoginScreenEvent> {

    override val container: Container<LoginScreenState, LoginScreenEvent> =
        container(LoginScreenState())

    fun tryKakaoLogin(activityContext: Context) = intent {

        postSideEffect(LoginScreenEvent.LoginRequested)

        loginRepository.kakaoLogin(activityContext).onSuccess {
            postSideEffect(LoginScreenEvent.LoginCompleted)
        }.onFailure {
            postSideEffect(LoginScreenEvent.LoginError("카카오 로그인 실패"))
            this@LoginViewModel.errorLog("카카오 로그인 실패")
        }

    }

}