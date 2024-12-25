package com.alreadyoccupiedseat.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B1_Regular
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B1_SemiBold
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B3_Regular


@Preview
@Composable
private fun PreviewShowPotAlert() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        repeat(3) {
            ShowPotAlert(
                imageUrl = "https://images.pexels.com/photos/6865046/pexels-photo-6865046.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                title = "Show Alert Title",
                content = "Show Alert Content",
                timeAt = "2024.12.25"
            )
        }
    }
}

@Composable
fun ShowPotAlert(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    content: String,
    timeAt: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        AsyncImage(
            modifier = Modifier.size(80.dp),
            model = imageUrl, contentDescription = "show image",
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.size(14.dp))
        Box {
            Column(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
            ) {
                ShowPotKoreanText_B1_SemiBold(
                    text = title,
                    color = Color.White,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))

                ShowPotKoreanText_B1_Regular(text = content, color = ShowpotColor.Gray200)

                Spacer(modifier = Modifier.height(5.dp))

                ShowPotKoreanText_B3_Regular(text = timeAt, color = ShowpotColor.Gray200)

            }
        }

    }
}