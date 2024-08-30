package com.alreadyoccupiedseat.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.IconMenuWithCount
import com.alreadyoccupiedseat.designsystem.component.IconMenuWithText
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1

@Composable
fun SettingsScreen(
    navController: NavController,
    versionName: String,
    onAccountClicked: () -> Unit,
    onPrivacyPolicyClicked: () -> Unit = { },
    onTermsOfServiceClicked: () -> Unit = { },
    onNotificationSettingClicked: () -> Unit = { },

    ) {
    SettingsScreenContent(
        versionName = versionName,
        onBackClicked = {
            navController.popBackStack()
        },
        onAccountClicked = {
            onAccountClicked()
        },
        onPrivacyPolicyClicked = onPrivacyPolicyClicked,
        onTermsOfServiceClicked = onTermsOfServiceClicked,
        onNotificationSettingClicked = onNotificationSettingClicked
    )
}

@Composable
fun SettingsScreenContent(
    versionName: String,
    onBackClicked: () -> Unit,
    onAccountClicked: () -> Unit,
    onPrivacyPolicyClicked: () -> Unit = { },
    onTermsOfServiceClicked: () -> Unit = { },
    onNotificationSettingClicked: () -> Unit = { },
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SettingsTopBar(
                onBackClicked = onBackClicked
            )
        },
        containerColor = ShowpotColor.Gray700
    ) { innerPadding ->

        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(innerPadding)
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            IconMenuWithText(
                firstIcon = painterResource(id = com.alreadyoccupiedseat.designsystem.R.drawable.ic_info_24),
                title = "버전 $versionName",
                // TODO: after MVP
                text = "최신 버전입니다",
                onClicked = {}
            )

            Spacer(modifier = Modifier.height(8.dp))

            IconMenuWithCount(
                firstIcon = painterResource(id = com.alreadyoccupiedseat.designsystem.R.drawable.ic_profile_24),
                title = stringResource(com.alreadyoccupiedseat.designsystem.R.string.account),
                onClicked = {
                    onAccountClicked()
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            IconMenuWithCount(
                firstIcon = painterResource(id = com.alreadyoccupiedseat.designsystem.R.drawable.ic_alarm_24_default),
                title = "알림 설정",
                onClicked = {
                    onNotificationSettingClicked()
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            IconMenuWithCount(
                firstIcon = painterResource(id = com.alreadyoccupiedseat.designsystem.R.drawable.ic_privacy_24),
                title = "개인정보 처리 방침",
                onClicked = {
                    onPrivacyPolicyClicked()
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            IconMenuWithCount(
                firstIcon = painterResource(id = com.alreadyoccupiedseat.designsystem.R.drawable.ic_report_24),
                title = "이용 약관",
                onClicked = {
                    onTermsOfServiceClicked()
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // TODO: after MVP
//            IconMenuWithCount(
//                firstIcon = painterResource(id = com.alreadyoccupiedseat.designsystem.R.drawable.ic_faq_24),
//                title = "카카오 문의하기",
//                onClicked = {}
//            )

        }

    }
}