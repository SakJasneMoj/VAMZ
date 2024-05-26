package com.example.vamzsem.ui.utils

import java.util.concurrent.TimeUnit

fun formatTime(totalTimeInSeconds: Long): String {
    val minutes = TimeUnit.SECONDS.toMinutes(totalTimeInSeconds)
    val seconds = totalTimeInSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}
