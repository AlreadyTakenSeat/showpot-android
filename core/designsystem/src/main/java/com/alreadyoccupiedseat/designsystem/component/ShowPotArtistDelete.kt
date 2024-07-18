package com.alreadyoccupiedseat.designsystem.component

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
    onIconClick: () -> Unit,
) {
    ShowPotArtist(
        icon = painterResource(id = R.drawable.img_artist_default),
        text = "High Flying Birds",
        content = {
            Image(
                painter = painterResource(id = R.drawable.ic_delete_24), // Genre 브런치와 합쳐지면 변경 필요
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