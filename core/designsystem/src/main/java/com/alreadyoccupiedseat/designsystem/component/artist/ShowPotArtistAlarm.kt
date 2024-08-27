package com.alreadyoccupiedseat.designsystem.component.artist

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
    imageUrl: String,
    isSubscribed: Boolean = false,
    onClick: () -> Unit = {},
) {
    ShowPotArtist(
        imageUrl = imageUrl,
        text = text,
        onClick = onClick,
        content = {
            Box(
                modifier = modifier
                    .size(100.dp)
                    .background(
                        if (isSubscribed) ShowpotColor.MainOrange.copy(0.5f)
                        else ShowpotColor.Gray700.copy(0.5f), CircleShape
                    )
            ) {
                Image(
                    painter = if (isSubscribed) painterResource(id = R.drawable.ic_alarm_checked_36)
                    else painterResource(id = R.drawable.ic_alarm_plus_36),
                    contentDescription = "Check",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = modifier
                        .align(Alignment.Center)

                )
            }
        }
    )
}