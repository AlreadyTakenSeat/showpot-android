package com.alreadyoccupiedseat.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B1_Regular

@Composable
fun SearchHistoryChip(
    text: String,
    onChipClicked: (String) -> Unit,
    onDeleteClicked: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                onChipClicked(text)
            }
            .clip(RoundedCornerShape(80.dp))
            .background(
                color = ShowpotColor.Gray700
            )
            .border(
                width = 1.dp,
                color = ShowpotColor.Gray300,
                shape = RoundedCornerShape(80.dp)
            )
            .padding(start = 14.dp, end = 8.dp)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShowPotKoreanText_B1_Regular(
            text = text, color = Color.White
        )

        Icon(
            modifier = Modifier.clickable {
              onDeleteClicked(text)
            },
            painter = painterResource(R.drawable.ic_cancel_24),
            contentDescription = stringResource(R.string.cancel_icon_description),
            tint = ShowpotColor.Gray100
        )
    }
}