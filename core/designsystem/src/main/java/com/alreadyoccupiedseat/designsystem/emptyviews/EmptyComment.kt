package com.alreadyoccupiedseat.designsystem.emptyviews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.component.DefaultScreenWhenEmpty

@Composable
fun EmptyComment(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        DefaultScreenWhenEmpty(
            imageResId = com.alreadyoccupiedseat.designsystem.R.drawable.img_message,
            text = stringResource(id = com.alreadyoccupiedseat.designsystem.R.string.first_message)
        )
        Spacer(modifier = Modifier.height(58.dp))
    }
}