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
import androidx.compose.foundation.lazy.LazyColumn
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
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B3_SemiBold
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H2
import com.alreadyoccupiedseat.enum.TicketingAlertTime
import com.alreadyoccupiedseat.model.TicketingBoxSelectionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketingNotificationBottomSheet(
    ticketingBoxSelectionState: List<TicketingBoxSelectionState>,
    onSelectionBoxClicked: (Int) -> Unit,
    onMainButtonClicked: () -> Unit,
    onDismissRequested: () -> Unit,
) {
    ShowPotBottomSheet(
        onDismissRequest = {
            onDismissRequested()
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                SheetHandler()
            }

            item {
                ShowPotKoreanText_H1(
                    modifier = Modifier.fillMaxWidth(),
                    text = "티켓팅 알림을 언제 받으실건가요?",
                    color = Color.White,
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            TicketingAlertTime.entries.forEachIndexed { index, curItem ->
                item {
                    TicketingNotificationSelectBox(
                        ticketingBoxSelectionState[index].isSelected,
                        ticketingBoxSelectionState[index].isAvailable,
                        curItem.beforeText,
                    ) {
                        onSelectionBoxClicked(index)
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            item {
                ShowPotMainButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "알림 설정하기"
                ) {
                    onMainButtonClicked()
                }
            }

            item {
                Spacer(modifier = Modifier.height(54.dp))
            }
        }
    }
}
