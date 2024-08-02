package com.alreadyoccupiedseat.designsystem.component.snackbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.alreadyoccupiedseat.designsystem.R

@Composable
fun CheckIconSnackbar(
    mainText: String,
    actionText: String,
    onIconClicked: () -> Unit,
    onActionTextClicked: () -> Unit
) {
    ShowPotSnackbar(
        iconPainter = painterResource(id = R.drawable.ic_check_36),
        mainText = mainText,
        actionText = actionText,
        onIconClicked = onIconClicked,
        onActionTextClicked = onActionTextClicked
    )
}