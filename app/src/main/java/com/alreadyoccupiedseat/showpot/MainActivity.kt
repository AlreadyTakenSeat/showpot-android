package com.alreadyoccupiedseat.showpot

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.alreadyoccupiedseat.designsystem.ShowPotTheme
import com.alreadyoccupiedseat.onboarding.OnboardingScreen
import com.alreadyoccupiedseat.showpot.ui.AppScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(Intent(this, ShowPotFcmService::class.java))

        setContent {
            // TODO: Improve the reactivity
            // ex) do not let them see the onboarding screen "at all" if they have already completed it
            ShowPotTheme {

                EssentialPermissionDialog()

                val viewModel = hiltViewModel<MainActivityViewModel>()
                val state = viewModel.state.collectAsState()


                when (state.value.isOnboardingCompleted) {

                    OnboardingCheckState.OnBoardingInit, OnboardingCheckState.OnBoardingChecking -> {
                        // TODO: Splash for Loading
                    }

                    OnboardingCheckState.OnBoardingDone -> {
                        AppScreen(
                            onPrivacyPolicyClicked = {
                                startActivity(getWebIntent("https://luxuriant-neighbor-e45.notion.site/d332eb11da37456b818eb596d05015db"))
                            },
                            onTermsOfServiceClicked = {
                                startActivity(getWebIntent("https://luxuriant-neighbor-e45.notion.site/c9a47093644a49299cfbabe81d8dc66a?pvs=4"))
                            },
                            onNotificationSettingClicked = {
                                navigateToNotificationSetting(this)
                            },
                            versionName = packageManager.getPackageInfo(
                                packageName,
                                0
                            ).versionName
                        )
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

private fun getWebIntent(url: String) = Intent(
    Intent.ACTION_VIEW,
    Uri.parse(url)
)

private fun navigateToNotificationSetting(context: Context) {
    val intent = Intent().also { intent ->
        intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }

    runCatching {
        context.startActivity(intent)
    }
}
