package com.alreadyoccupiedseat.designsystem.component.bottomSheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.RectangleShape
import com.alreadyoccupiedseat.designsystem.ShowpotColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowPotBottomSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
    content: @Composable () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest()
        },
        sheetState = sheetState,
        shape = RectangleShape,
        containerColor = ShowpotColor.Gray600,
        dragHandle = null
    ) {
        content()
    }
}