package com.example.vamzsem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun GradientBackground(
    colors: List<Color> = listOf(
        Color(0xFFFFDAB9), // Peach Puff
        Color(0xFFF5DEB3), // Wheat
        Color(0xFFF5F5DC)  // Beige
    ),
    startY: Float = 0.0f,
    endY: Float = Float.POSITIVE_INFINITY
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = colors,
                    startY = startY,
                    endY = endY
                )
            )
    )
}
