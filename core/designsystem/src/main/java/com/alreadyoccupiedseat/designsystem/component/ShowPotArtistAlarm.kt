package com.alreadyoccupiedseat.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor

@Composable
fun ShowPotArtistAlarm(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean = false, // 구독 버튼 컴포저블이 필요함
    onClick: () -> Unit = {},
    onIconClick: () -> Unit,
) {
    ShowPotArtist(
        icon = painterResource(id = R.drawable.img_artist_default),
        text = text,
        onClick = onClick,
        content = {
            Box(
                modifier = modifier
                    .size(100.dp)
                    .background(ShowpotColor.Gray700.copy(0.5f), CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_alarm_plus_36),
                    contentDescription = "Check",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = modifier
                        .align(Alignment.Center)
                        .clickable {
                            onIconClick()
                        }
                )
            }
        }
    )
}