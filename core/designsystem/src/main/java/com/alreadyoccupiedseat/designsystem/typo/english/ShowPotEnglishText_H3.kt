package com.alreadyoccupiedseat.designsystem.typo.english

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.alreadyoccupiedseat.designsystem.ShowPotTypography
import com.alreadyoccupiedseat.designsystem.typo.ShowPotBaseText

@Composable
fun ShowPotEnglishText_H3(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    ShowPotBaseText(
        text = text,
        modifier = modifier,
        style = ShowPotTypography.English.H3.copy(color = color),
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
    )
}