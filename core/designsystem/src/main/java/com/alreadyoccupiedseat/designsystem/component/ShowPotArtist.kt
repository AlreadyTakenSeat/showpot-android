package com.alreadyoccupiedseat.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.typo.english.ShowPotEnglishText_H5

@Composable
fun ShowPotArtist(
    modifier: Modifier = Modifier,
    text: String,
    icon: Painter,
    onClick: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        Box {
            Image(
                modifier = modifier
                    .align(Alignment.Center)
                    .size(100.dp)
                    .clip(CircleShape)
                    .clickable {
                        onClick()
                    },
                painter = icon,
                contentDescription = "Artist Image",
            )
            content()
        }
        ShowPotEnglishText_H5(
            modifier = modifier.width(100.dp),
            text = text,
            color = ShowpotColor.Gray100,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
