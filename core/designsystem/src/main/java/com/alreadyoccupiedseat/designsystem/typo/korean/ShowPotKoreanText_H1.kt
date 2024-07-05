package com.alreadyoccupiedseat.designsystem.typo.korean

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.alreadyoccupiedseat.designsystem.ShowPotTypography
import com.alreadyoccupiedseat.designsystem.typo.ShowPotBaseText

@Composable
fun ShowPotKoreanText_H1(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Black,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    ShowPotBaseText(
        text = text,
        modifier = modifier,
        style = ShowPotTypography.Korean.H1.copy(
            color = color
        )
    )
}