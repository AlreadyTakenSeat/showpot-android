package com.alreadyoccupiedseat.designsystem.component.bottomSheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.core.extension.conditional
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B3_SemiBold
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H2

@Composable
fun TicketingNotificationSelectBox(
    isSelected: Boolean,
    isAvailable: Boolean,
    beforeTime: String,
    onClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(ShowpotColor.Gray500, RoundedCornerShape(2.dp))
            .conditional(isSelected) {
                border(1.dp, ShowpotColor.MainOrange, RoundedCornerShape(2.dp))
            }
            .conditional(isAvailable) {
                clickable {
                    onClicked()
                }
            }
            .padding(
                horizontal = 24.dp,
                vertical = 14.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ShowPotKoreanText_H2(
            text = "티켓팅 $beforeTime",
            color = if (isAvailable) Color.White else ShowpotColor.Gray400
        )

        if (isAvailable) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = if (isSelected) painterResource(id = R.drawable.ic_checked_checkbox_24) else
                    painterResource(id = R.drawable.ic_checkbox_24),
                contentDescription = "check box",
            )
        } else {
            ShowPotKoreanText_B3_SemiBold(
                text = "해당 시간 선택은 불가능해요",
                color = ShowpotColor.MainOrange
            )
        }

    }
}