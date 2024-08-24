package com.alreadyoccupiedseat.showpot

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import com.alreadyoccupiedseat.designsystem.ShowPotTheme
import com.alreadyoccupiedseat.onboarding.OnboardingScreen
import com.alreadyoccupiedseat.showpot.ui.AppScreen
import dagger.hilt.android.AndroidEntryPoint

val LocalAccessToken = compositionLocalOf<String?> {
    null
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var accessToken: String? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(Intent(this, ShowPotFcmService::class.java))

        setContent {
            // TODO: Improve the reactivity
            // ex) do not let them see the onboarding screen "at all" if they have already completed it
            ShowPotTheme {

                CompositionLocalProvider(LocalAccessToken provides accessToken) {

                    EssentialPermissionDialog()

                    val viewModel = hiltViewModel<MainActivityViewModel>()
                    val state = viewModel.state.collectAsState()

                    // 최상단 상태에서 LocalAccessToken으로 로그인 여부를 확인
                    // null 이라면 비로그인 상태
                    LaunchedEffect(true) {
                        viewModel.setInitAccessToken {
                            accessToken = it
                        }
                    }

                    when (state.value.isOnboardingCompleted) {

                        OnboardingCheckState.OnBoardingInit, OnboardingCheckState.OnBoardingChecking -> {
                            // TODO: Splash for Loading
                        }

                        OnboardingCheckState.OnBoardingDone -> {
                            AppScreen()
                        }

                        OnboardingCheckState.OnBoardingIsNotDone -> {
                            OnboardingScreen {
                                viewModel.onboardingCompleted()
                            }
                        }
                    }
                }
            }
        }
    }
}