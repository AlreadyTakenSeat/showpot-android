package com.alreadyoccupiedseat.designsystem.component.inputBox

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowPotTypography
import com.alreadyoccupiedseat.designsystem.ShowpotColor

@Composable
fun ShowCommentInputBox(
    inputText: String,
    onValueChange: (String) -> Unit,
    hint: String,
    backGroundColor: Color = ShowpotColor.Gray700,
    isPreviewComment: Boolean = false,
    onSendButtonClicked: () -> Unit,
) {

    BasicTextField(
        value = inputText,
        onValueChange = {
            onValueChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(backGroundColor),
        textStyle = ShowPotTypography.Korean.B2_regular.copy(Color.White),
        cursorBrush = SolidColor(ShowpotColor.Gray400),
        singleLine = true,
    ) { innerTextField ->

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(shape = RoundedCornerShape(4.dp))
                    .background(ShowpotColor.Gray600)
                    .padding(horizontal = 12.dp, vertical = 9.5.dp),
            ) {
                innerTextField()

                if (inputText.isEmpty()) {
                    Text(
                        text = hint,
                        style = ShowPotTypography.Korean.B2_regular.copy(
                            color = ShowpotColor.Gray400
                        ),
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(24.dp)
                    .clickable {
                        onSendButtonClicked()
                    },
                painter = painterResource(id = R.drawable.ic_send_24),
                contentDescription = "message send button",
                tint = if (inputText.isEmpty() && !isPreviewComment) ShowpotColor.Gray400 else Color.White
            )
        }
    }

}