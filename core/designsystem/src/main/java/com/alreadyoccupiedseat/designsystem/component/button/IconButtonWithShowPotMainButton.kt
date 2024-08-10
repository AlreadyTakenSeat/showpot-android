package com.alreadyoccupiedseat.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotMainButton

@Composable
fun IconButtonWithShowPotMainButton(
    icon: Painter,
    text: String,
    onIconButtonClicked: () -> Unit,
    onMainButtonClicked: () -> Unit,
) {
    Row(
        modifier = Modifier.run {
            fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 9.dp, bottom = 54.dp)
        },
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier.size(55.dp)
                .background(ShowpotColor.Gray500)
                .clickable {
                    onIconButtonClicked()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = "icon",
                tint = ShowpotColor.Gray200,
            )
        }

        Spacer(modifier = Modifier.width(15.dp))

        ShowPotMainButton(
            text = text,
        ) {
            onMainButtonClicked()
        }
    }
}