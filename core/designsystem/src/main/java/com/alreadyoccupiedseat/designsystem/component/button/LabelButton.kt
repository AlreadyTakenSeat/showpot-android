package com.alreadyoccupiedseat.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B2_Regular

@Composable
fun LabelButton(
    backgroundColor: Color,
    text: String,
    onClicked: () -> Unit = {}
) {

    Row(
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(2.dp))
            .clickable {
                onClicked()
            }
            .padding(vertical = 5.dp)
            .padding(start = 9.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShowPotKoreanText_B2_Regular(
            text = text,
            color = Color.White
        )

        Spacer(modifier = Modifier.width(1.dp))

        Icon(
            painter =
            painterResource(id = R.drawable.ic_arrow_16_right), contentDescription = "back arrow",
            tint = Color.White
        )

    }
}