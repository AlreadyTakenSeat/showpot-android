package com.alreadyoccupiedseat.subscription_genre

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
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotMainButton
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.SheetHandler
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.ShowPotBottomSheet
import com.alreadyoccupiedseat.designsystem.typo.english.ShowPotEnglishText_H1
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnsubscribeBottomSheet(
    genreName: String,
    onUnsubscribe: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    ShowPotBottomSheet(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SheetHandler()

            ShowPotEnglishText_H1(
                modifier = Modifier.fillMaxWidth(),
                text = genreName,
                color = ShowpotColor.White
            )

            Spacer(modifier = Modifier.height(1.dp))

            ShowPotKoreanText_H1(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.subscribe_cancel_the_subscription),
                color = ShowpotColor.White
            )

            Spacer(modifier = Modifier.height(19.dp))

            ShowPotMainButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.subscribe_unsubscribe)
            ) {
                onUnsubscribe()
            }

            Spacer(modifier = Modifier.height(54.dp))
        }
    }
}