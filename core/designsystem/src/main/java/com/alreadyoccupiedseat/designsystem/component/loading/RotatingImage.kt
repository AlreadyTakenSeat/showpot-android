package com.alreadyoccupiedseat.designsystem.component.loading

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun RotatingImage(
    modifier: Modifier,
    painter: Painter,
) {
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            rotation.animateTo(
                targetValue = -360f,
                animationSpec = tween(durationMillis = 3000, easing = LinearEasing)
            )
            rotation.snapTo(0f)
        }
    }

    Image(
        painter = painter,
        contentDescription = "Rotating Image",
        modifier = modifier
            .graphicsLayer(rotationZ = rotation.value)
    )
}