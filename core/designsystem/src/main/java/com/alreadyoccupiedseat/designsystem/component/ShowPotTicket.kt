package com.alreadyoccupiedseat.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.typo.english.ShowPotEnglishText_H3
import com.alreadyoccupiedseat.designsystem.typo.english.ShowPotEnglishText_H5
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B2_Regular

@Composable
fun ShowPotTicket(
    modifier: Modifier = Modifier,
    brush: Brush,
    image: Painter,
    showTime: String,
    showTimeTextColor: Color = ShowpotColor.MainYellow,
    showName: String,
    showLocation: String,
) {

    Box(
        modifier = modifier
            .height(106.dp)
//            .fillMaxWidth()
            .background(ShowpotColor.White)
    ) {

        Image(
            painter = image,
            contentDescription = "Show Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .alpha(0.3f)
                .size(width = 178.50415.dp, height = 106.dp)
                .align(Alignment.CenterEnd)
        )

        Box(
            modifier = Modifier
                .width(117.dp)
                .fillMaxHeight()
                .background(
                    brush = brush
                )
                .align(Alignment.Center)
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    vertical = 12.5.dp,
                    horizontal = 16.dp
                )
        ) {
            ShowPotEnglishText_H5(
                modifier = Modifier
                    .background(ShowpotColor.MainRed),
                text = showTime,
                color = showTimeTextColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            ShowPotEnglishText_H3(
                modifier = Modifier
                    .background(ShowpotColor.MainYellow)
                    .padding(top = 3.dp),
                text = showName,
                color = ShowpotColor.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            ShowPotKoreanText_B2_Regular(
                modifier = Modifier
                    .background(ShowpotColor.MainGreen)
                    .padding(top = 3.dp),
                text = showLocation,
                color = ShowpotColor.Gray300
            )
        }
    }
}