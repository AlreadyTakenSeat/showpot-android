package com.alreadyoccupiedseat.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H2

@Composable
fun ShowPotMainButtonWithIcon(
    modifier: Modifier = Modifier,
    text: String,
    icon: Painter,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonColors(
        containerColor = ShowpotColor.MainRed,
        contentColor = Color.Black,
        disabledContainerColor = ShowpotColor.Gray600,
        disabledContentColor = ShowpotColor.Gray400,
    ),
    onClicked: () -> Unit,
) {
    Button(
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp),
        colors = colors,
        shape = RectangleShape,
        onClick = onClicked,
    ) {
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        ) {
            Image(
                painter = icon,
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(12.dp))

            ShowPotKoreanText_H2(
                text = text,
                color = if (enabled) Color.Black else ShowpotColor.Gray400
            )
        }
    }
}