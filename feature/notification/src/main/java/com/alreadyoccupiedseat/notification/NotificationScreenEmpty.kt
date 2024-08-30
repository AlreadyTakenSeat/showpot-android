package com.alreadyoccupiedseat.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.component.DefaultScreenWhenEmpty
import com.alreadyoccupiedseat.designsystem.component.button.ShowPotSubButton

@Composable
fun NotificationScreenEmpty(
    isLoggedIn: Boolean,
    onLoginRequested: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 72.dp)
            .fillMaxWidth()
    ) {

        DefaultScreenWhenEmpty(
            imageResId = com.alreadyoccupiedseat.designsystem.R.drawable.img_empty_alarm,
            text = "알림 설정한 공연이 없어요"
        )

        Spacer(modifier = Modifier.height(96.dp))

        if (isLoggedIn) {
            Spacer(modifier = Modifier.height(55.dp))
        } else {
            ShowPotSubButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "로그인 하러 가기",
                onClicked = {
                    onLoginRequested()
                }
            )
        }
    }
}