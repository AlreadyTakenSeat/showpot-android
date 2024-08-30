package com.alreadyoccupiedseat.designsystem.component.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B1_SemiBold

@Composable
fun ShowPotSnackbar(
    iconPainter: Painter,
    mainText: String,
    actionText: String? = null,
    onIconClicked: () -> Unit,
    onActionTextClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 42.dp)
            .fillMaxWidth()
            .height(46.dp)
            .background(ShowpotColor.Gray500),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            modifier = Modifier
                .padding(start = 7.dp, end = 3.dp)
                .clickable {
                    onIconClicked()
                },
            painter = iconPainter,
            tint = ShowpotColor.Gray200,
            contentDescription = "snackbar icon"
        )

        ShowPotKoreanText_B1_SemiBold(
            modifier = Modifier.weight(1f),
            text = mainText,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        actionText?.let {
            ShowPotKoreanText_B1_SemiBold(
                modifier = Modifier
                    .padding(
                        start = 16.dp, end = 13.dp
                    )
                    .clickable {
                        onActionTextClicked()
                    },
                text = actionText,
                color = ShowpotColor.MainOrange,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}