package com.alreadyoccupiedseat.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B3_Regular
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1

@Preview
@Composable
fun PreviewShowInfo() {
    ShowInfo(
        imageUrl = "https://cdn.electimes.com/news/photo/202003/195245_108558.jpg",
        showTitle = "Show Title",
        dateInfo = "2022.12.31",
        locationInfo = "Seoul",
    )
}

@Composable
fun ShowInfo(
    modifier: Modifier = Modifier,
    imageUrl: String,
    showTitle: String,
    dateInfo: String,
    locationInfo: String,
    icon: @Composable () -> Unit = {},
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier.size(80.dp),
            model = imageUrl, contentDescription = "show image",
            contentScale = ContentScale.Crop)

        Spacer(modifier = Modifier.size(14.dp))

        Box {

            Column(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)) {

                ShowPotKoreanText_H1(text = showTitle, color = Color.White, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)

                Spacer(modifier = Modifier.height(5.dp))

                ShowPotKoreanText_B3_Regular(text = dateInfo, color = ShowpotColor.Gray200)

                Spacer(modifier = Modifier.height(1.dp))

                ShowPotKoreanText_B3_Regular(text = locationInfo, color = ShowpotColor.Gray200)

            }

            Box(modifier = Modifier.align(Alignment.BottomEnd) ) {
                icon()
            }
        }

    }
}