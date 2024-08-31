package com.alreadyoccupiedseat.designsystem.component.artist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.alreadyoccupiedseat.designsystem.R

@Composable
fun ShowPotArtistDelete(
    modifier: Modifier = Modifier,
    name: String,
    imageUrl: String,
    onIconClick: () -> Unit,
) {
    ShowPotArtist(
        imageUrl = imageUrl,
        text = name,
        content = {
            Image(
                painter = painterResource(id = R.drawable.ic_circle_delete_40), // Genre 브런치와 합쳐지면 변경 필요
                contentDescription = "Delete",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clickable {
                       onIconClick()
                    }
            )
        }
    )
}