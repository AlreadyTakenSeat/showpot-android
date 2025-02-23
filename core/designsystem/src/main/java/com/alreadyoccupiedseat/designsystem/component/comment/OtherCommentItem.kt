package com.alreadyoccupiedseat.designsystem.component.comment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alreadyoccupiedseat.common.utils.convertToKoreanTimeFormat
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B2_Regular
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B3_Regular

@Composable
fun OtherCommentItem(
    modifier: Modifier = Modifier,
    profileUrl: String,
    userName: String,
    content: String,
    createdAt: String,
    onIconClicked: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        AsyncImage(
            model = profileUrl,
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(30.dp)
                .clip(shape = CircleShape)
        )

        Column {

            ShowPotKoreanText_B3_Regular(
                text = userName,
                color = Color.White
            )

            Spacer( Modifier.height(7.dp))

            Row(
                verticalAlignment = Alignment.Bottom
            ) {

                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(ShowpotColor.Gray600)
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    ShowPotKoreanText_B2_Regular(
                        text = content,
                        color = ShowpotColor.Gray100
                    )
                }

                Spacer(modifier = Modifier.width(7.dp))

                ShowPotKoreanText_B3_Regular(
                    text = convertToKoreanTimeFormat(
                        createdAt
                    ),
                    color = ShowpotColor.Gray300
                )

                Icon(
                    modifier = Modifier.size(16.dp)
                        .clickable {
                            onIconClicked()
                        },
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More",
                    tint = ShowpotColor.Gray300
                )
            }
        }
    }
}