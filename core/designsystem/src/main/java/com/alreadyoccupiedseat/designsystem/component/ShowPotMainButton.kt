package com.alreadyoccupiedseat.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H2

@Composable
fun ShowPotMainButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClicked: () -> Unit,
) {

    ShowPotButton(
        onClick = {
            onClicked()
        },
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(ShowpotColor.MainOrange),
        colors = ButtonColors(
            containerColor = ShowpotColor.MainOrange,
            contentColor = Color.Black,
            disabledContainerColor = ShowpotColor.Gray600,
            disabledContentColor = ShowpotColor.Gray400,
        ),
        content = {
            ShowPotKoreanText_H2(
                text = text,
                color = if (enabled) Color.Black else ShowpotColor.Gray400
            )
        }
    )

}