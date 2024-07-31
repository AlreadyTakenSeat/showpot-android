package com.alreadyoccupiedseat.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.typo.english.ShowPotEnglishText_H4

@Composable
fun RecommendedShow(
    modifier: Modifier = Modifier,
    imageUrl: String,
    text: String,
    color: Color = ShowpotColor.White,
    onClick: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .size(width = 192.dp, height = 309.dp)
            .clip(RoundedCornerShape(2.dp))
            .clickable {
                onClick()
            }
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "Show Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
        )

        ShowPotEnglishText_H4(
            modifier = Modifier
                .background(ShowpotColor.Gray500)
                .fillMaxWidth()
                .padding(
                    vertical = 11.dp,
                    horizontal = 14.dp
                ),
            text = text,
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

    }

}