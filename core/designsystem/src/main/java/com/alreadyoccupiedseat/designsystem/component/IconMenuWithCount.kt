package com.alreadyoccupiedseat.designsystem.component

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B1_SemiBold
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1

@Preview
@Composable
private fun IconMenuWithCountPreview(modifier: Modifier = Modifier) {
    IconMenuWithCount(
        modifier = modifier,
        firstIcon = painterResource(id = R.drawable.ic_arrow_16_right),
        title = "알림",
        tint = ShowpotColor.Gray100,
        count = 3
    )
}

@Composable
fun IconMenuWithCount(
    modifier: Modifier = Modifier,
    firstIcon: Painter,
    title: String,
    tint: Color = ShowpotColor.Gray300,
    count: Int? = null,
    onClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClicked()
            }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = firstIcon,
            contentDescription = "icon",
            tint = ShowpotColor.Gray300,
            modifier = Modifier.size(24.dp)
        )

        Spacer(Modifier.width(10.dp))

        ShowPotKoreanText_H1(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 4.dp),
            text = title,
            color = ShowpotColor.Gray100,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (count != null) {
                ShowPotKoreanText_B1_SemiBold(
                    text = count.toString(),
                    color = ShowpotColor.Gray100,
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_24_right),
                contentDescription = "icon",
                tint = tint,
                modifier = Modifier.size(24.dp)
            )
        }

    }
}