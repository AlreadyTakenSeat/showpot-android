package com.alreadyoccupiedseat.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1

@Composable
fun ShowPotMenu(
    modifier: Modifier = Modifier,
    text: String,
    startIcon: Painter? = null,
    endIcon: Painter? = null,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .background(ShowpotColor.Gray700)
            .height(44.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        startIcon?.let {
            Image(
                modifier = Modifier.padding(start = 12.dp),
                painter = it,
                contentDescription = "startIcon",
                colorFilter = tint(ShowpotColor.Gray100)
            )
        }

        Spacer(modifier = Modifier.width(if (startIcon != null) 10.dp else 16.dp))

        ShowPotKoreanText_H1(
            text = text,
            color = ShowpotColor.Gray100,
            modifier = Modifier.weight(1f)
        )

        endIcon?.let {
            Image(
                painter = it,
                contentDescription = "endIcon",
                colorFilter = tint(ShowpotColor.Gray100),
                modifier = Modifier.clickable { onClick() }
            )
        }
    }
}