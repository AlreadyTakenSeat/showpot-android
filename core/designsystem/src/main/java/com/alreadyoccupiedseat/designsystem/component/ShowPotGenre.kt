package com.alreadyoccupiedseat.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.alreadyoccupiedseat.core.extension.conditional
import com.alreadyoccupiedseat.designsystem.R

@Composable
fun ShowPotGenre(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    icon: Painter,
    isDeletable: Boolean = false,
    onSelectClicked: () -> Unit = {},
    onDeleteClicked: () -> Unit = {},
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = icon, contentDescription = "장르",
            modifier = modifier
                .conditional(enabled) {
                    clickable {
                        onSelectClicked()
                    }
                },
        )
        if (isDeletable) {
            Image(
                painter = painterResource(id = R.drawable.ic_circle_delete_40),
                contentDescription = "Delete",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clickable { onDeleteClicked() }
            )
        }
    }
}
