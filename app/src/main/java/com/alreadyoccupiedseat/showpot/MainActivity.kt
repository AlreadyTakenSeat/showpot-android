package com.alreadyoccupiedseat.showpot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.alreadyoccupiedseat.designsystem.ShowPotTheme
import com.alreadyoccupiedseat.onboarding.OnboardingScreen
import com.alreadyoccupiedseat.showpot.ui.AppScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // TODO: Improve the reactivity
            // ex) do not let them see the onboarding screen "at all" if they have already completed it
            ShowPotTheme {
                val viewModel = hiltViewModel<MainActivityViewModel>()
                val state = viewModel.state.collectAsState()

                if (state.value.isOnboardingCompleted) {
                    AppScreen()
                } else {
                    OnboardingScreen {
                        viewModel.onboardingCompleted()
                    }
                }
            }
        }
    }
}