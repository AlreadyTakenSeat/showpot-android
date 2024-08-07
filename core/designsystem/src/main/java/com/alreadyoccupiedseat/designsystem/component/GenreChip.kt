package com.alreadyoccupiedseat.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B1_Regular

@Composable
fun GenreChip(
    genre: String
) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = ShowpotColor.Gray400,
                shape = RoundedCornerShape(80.dp)
            )
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        ShowPotKoreanText_B1_Regular(
            text = genre,
            color = Color.White,
        )
    }
}