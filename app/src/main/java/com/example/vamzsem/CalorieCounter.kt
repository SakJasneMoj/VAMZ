// CalorieCounter.kt
package com.example.vamzsem

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun CalorieCounter(viewModel: CalorieCounterViewModel = viewModel()) {
    val calories = viewModel.calories

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DrawCalorieArc(calories, 1)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { viewModel.incrementCalories() }) {
                Text("Add")
            }
            Button(onClick = { viewModel.decrementCalories() }) {
                Text("Subtract")
            }
        }
    }
}

@Composable
fun DrawCalorieArc(currentCalories: Int, maxCalories: Int) {
    val progress = currentCalories.toFloat() / maxCalories
    val arcColor = if (currentCalories <= maxCalories) Color(0xFF66BB6A) else Color(0xFFEF5350)
    val displayProgress = if (currentCalories <= maxCalories) progress else 1f

    val robotoSlabFontFamily = FontFamily(
        Font(R.font.robotoslab_regular),
        Font(R.font.robotoslab_bold, FontWeight.Bold)
    )

    Box(contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(200.dp)) {
            drawArcWithAngle(
                startAngle = 135f,
                sweepAngle = 270f * displayProgress,
                color = arcColor,
                strokeWidth = 40f
            )
            drawArcWithAngle(
                startAngle = 135f + 270f * displayProgress,
                sweepAngle = 270f * (1 - displayProgress),
                color = Color(0xFFB0BEC5),
                strokeWidth = 40f
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$currentCalories",
                style = TextStyle(
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = robotoSlabFontFamily
                )
            )
            Text(
                text = "Calories",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = robotoSlabFontFamily
                )
            )
        }
    }
}

fun DrawScope.drawArcWithAngle(
    startAngle: Float,
    sweepAngle: Float,
    color: Color,
    strokeWidth: Float
) {
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(strokeWidth)
    )
}