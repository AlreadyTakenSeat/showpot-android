package com.alreadyoccupiedseat.designsystem.component.bottomSheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.core.extension.conditional
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotMainButton
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketingNotificationBottomSheet(
    firstItemSelected: Boolean,
    secondItemSelected: Boolean,
    thirdItemSelected: Boolean,
    onFirstItemClicked: () -> Unit,
    onSecondItemClicked: () -> Unit,
    onThirdItemClicked: () -> Unit,
    onMainButtonClicked: () -> Unit,
    onDismissRequested: () -> Unit,
) {
    ShowPotBottomSheet(
        onDismissRequest = {
            onDismissRequested()
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SheetHandler()

            ShowPotKoreanText_H1(
                modifier = Modifier.fillMaxWidth(),
                text = "티켓팅 알림을 언제 받으실건가요?",
                color = Color.White,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ShowpotColor.Gray500, RoundedCornerShape(2.dp))
                    .conditional(firstItemSelected) {
                        border(1.dp, ShowpotColor.MainOrange, RoundedCornerShape(2.dp))
                    }
                    .padding(
                        horizontal = 24.dp,
                        vertical = 14.dp
                    )
                    .clickable {
                        onFirstItemClicked()
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ShowPotKoreanText_H2(
                    text = "티켓팅 24시간 전",
                    color = Color.White
                )

                Image(
                    modifier = Modifier.size(24.dp),
                    painter = if (firstItemSelected) painterResource(id = R.drawable.ic_checked_checkbox_24) else
                        painterResource(id = R.drawable.ic_checkbox_24),
                    contentDescription = "first check box",
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ShowpotColor.Gray500, RoundedCornerShape(2.dp))
                    .conditional(secondItemSelected) {
                        border(1.dp, ShowpotColor.MainOrange, RoundedCornerShape(2.dp))
                    }
                    .padding(
                        horizontal = 24.dp,
                        vertical = 14.dp
                    )
                    .clickable {
                        onSecondItemClicked()
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ShowPotKoreanText_H2(
                    text = "티켓팅 6시간 전",
                    color = Color.White
                )

                Image(
                    modifier = Modifier.size(24.dp),
                    painter = if (secondItemSelected) painterResource(id = R.drawable.ic_checked_checkbox_24) else
                        painterResource(id = R.drawable.ic_checkbox_24),
                    contentDescription = "second check box",
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ShowpotColor.Gray500, RoundedCornerShape(2.dp))
                    .conditional(thirdItemSelected) {
                        border(1.dp, ShowpotColor.MainOrange, RoundedCornerShape(2.dp))
                    }
                    .padding(
                        horizontal = 24.dp,
                        vertical = 14.dp
                    )
                    .clickable {
                        onThirdItemClicked()
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ShowPotKoreanText_H2(
                    text = "티켓팅 1시간 전",
                    color = Color.White
                )

                Image(
                    modifier = Modifier.size(24.dp),
                    painter = if (thirdItemSelected) painterResource(id = R.drawable.ic_checked_checkbox_24) else
                        painterResource(id = R.drawable.ic_checkbox_24),
                    contentDescription = "second check box",
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            ShowPotMainButton(
                modifier = Modifier.fillMaxWidth(),
                text = "알림 설정하기"
            ) {
                onMainButtonClicked()
            }

            Spacer(modifier = Modifier.height(54.dp))
        }
    }
}
