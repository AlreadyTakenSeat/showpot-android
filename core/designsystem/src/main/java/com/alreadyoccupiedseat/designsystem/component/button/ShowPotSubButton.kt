package com.alreadyoccupiedseat.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotButton
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H2

@Composable
fun ShowPotSubButton(
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
            .background(ShowpotColor.Gray400),
        colors = ButtonColors(
            containerColor = ShowpotColor.Gray400,
            contentColor = Color.Black,
            disabledContainerColor = ShowpotColor.Gray600,
            disabledContentColor = ShowpotColor.Gray400,
        ),
        content = {
            ShowPotKoreanText_H2(
                text = text,
                color = if (enabled) Color.White else ShowpotColor.Gray400
            )
        }
    )

}