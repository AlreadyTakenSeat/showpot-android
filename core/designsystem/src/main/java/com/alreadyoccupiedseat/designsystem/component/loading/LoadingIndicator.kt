package com.alreadyoccupiedseat.designsystem.component.loading

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.R

@Composable
fun LoadingIndicator() {
    RotatingImage(
        modifier = Modifier.size(50.dp)
            .padding(1.dp),
        painter = painterResource(R.drawable.img_loading_indicator),
        speed = 2000,
    )
}