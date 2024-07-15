package com.alreadyoccupiedseat.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.core.extension.conditional
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowPotTypography
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B1_SemiBold

@Composable
fun ShowPotSearchBar(
    modifier: Modifier = Modifier,
    hint: String = String.EMPTY,
    inputText: String = String.EMPTY,
    enabled: Boolean = true,
    onClickedWhenDisEnabled: () -> Unit = {},
    onCancelClicked: () -> Unit = {},
    onTextChanged: (String) -> Unit = {}
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(color = ShowpotColor.Gray600)
            .conditional(enabled.not()) {
                clickable {
                    onClickedWhenDisEnabled()
                }
            }
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            modifier = Modifier
                .weight(1f),
            value = inputText,
            enabled = enabled,
            onValueChange = { newString ->
                onTextChanged(newString)
            },
            textStyle = ShowPotTypography.Korean.B1_semiBold.copy(color = Color.White),
            cursorBrush = SolidColor(Color.White),
            singleLine = true,
        ) { innerTextField ->
            innerTextField()

            // hint section
            if (inputText.isEmpty()) {
                ShowPotKoreanText_B1_SemiBold(
                    text = hint,
                    color = ShowpotColor.Gray400
                )
            }
        }

        if (enabled) {
            Icon(
                modifier = Modifier.clickable {
                    onCancelClicked()
                },
                painter = painterResource(id = R.drawable.ic_cancel_36),
                contentDescription = stringResource(R.string.cancel_icon_description),
                tint = Color.White,
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_magnifier_36),
                contentDescription = stringResource(R.string.magnifier_icon_description),
                tint = Color.White,
            )
        }
    }
}