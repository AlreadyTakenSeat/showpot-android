package com.alreadyoccupiedseat.myalarm_setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.SheetHandler
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.ShowPotBottomSheet
import com.alreadyoccupiedseat.designsystem.component.button.ShowPotSubButton
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1

@Preview
@Composable
fun PreviewAlarmOptionsBottomSheet(modifier: Modifier = Modifier) {
    AlarmOptionsBottomSheet(
        onTicketSheetVisible = {},
        onDismissRequest = {},
        onRemoveClicked = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmOptionsBottomSheet(
    onTicketSheetVisible: () -> Unit,
    onDismissRequest: () -> Unit,
    onRemoveClicked: () -> Unit,
) {
    ShowPotBottomSheet(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SheetHandler()

            ShowPotKoreanText_H1(
                text = stringResource(id = R.string.alarm),
                color = ShowpotColor.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            ShowPotSubButton(
                modifier = Modifier
                    .padding(top = 16.dp),
                text = stringResource(id = R.string.action_change),
                onClicked = {
                    onTicketSheetVisible()
                    onDismissRequest()
                }
            )

            ShowPotSubButton(
                modifier = Modifier
                    .padding(top = 12.dp),
                text = stringResource(id = R.string.action_turn_off),
                onClicked = {
                    onRemoveClicked()
                }
            )

            Spacer(modifier = Modifier.height(54.dp))
        }
    }
}