package com.alreadyoccupiedseat.show_detail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B1_Regular

@Composable
fun HorizontalTitleAndInfoText(
    modifier: Modifier = Modifier,
    title: String,
    infoText: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        ShowPotKoreanText_B1_Regular(
            text = title,
            color = ShowpotColor.Gray300,
        )

        Spacer(modifier = Modifier.width(10.dp))

        ShowPotKoreanText_B1_Regular(
            text = infoText,
            color = ShowpotColor.Gray200,
        )
    }
}