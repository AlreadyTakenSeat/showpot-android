package com.alreadyoccupiedseat.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.alreadyoccupiedseat.core.extention.conditional
import com.alreadyoccupiedseat.designsystem.R

@Composable
fun ShowPotGenre(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    icon: Painter,
    selectedIcon: Painter? = null,
    isSelected: Boolean = false,
    isDeletable: Boolean = false,
    onSelectClicked: () -> Unit = {},
    onDeleteClicked: () -> Unit = {}
) {
    val displayIcon = if (isSelected && selectedIcon != null) selectedIcon else icon

    Box(
        modifier = modifier
            .conditional(enabled) {
                clickable {
                    onSelectClicked()
                }
            }
    ) {
        Image(painter = displayIcon, contentDescription = "장르")
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
