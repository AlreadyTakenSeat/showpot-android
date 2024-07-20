package com.alreadyoccupiedseat.designsystem

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun ShowPotTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current

    val statusBarColor by remember { mutableIntStateOf(ShowpotColor.Gray700.toArgb()) }

    if (view.isInEditMode.not()) {
        SideEffect {
            val window = (view.context as Activity).window

            window.statusBarColor = statusBarColor
            // status bar icon color(light or dark)
            // if false, status bar icon color is dark
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        content = content
    )
}