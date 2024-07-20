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
fun ShowPotArtistSubscription(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
    onIconClick: () -> Unit,
) {
    ShowPotArtist(
        icon = painterResource(id = R.drawable.img_artist_default),
        text = text,
        onClick = onClick,
        content = {
            if (isSelected) {
                Box(
                    modifier = modifier
                        .size(100.dp)
                        .background(ShowpotColor.MainRed.copy(alpha = 0.7f), CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_check_36),
                        contentDescription = "Check",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clickable {
                                onIconClick()
                            }
                    )
                }
            }

        }
    )
}