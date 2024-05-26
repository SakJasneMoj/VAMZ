package com.example.vamzsem.ui.components

import android.icu.text.CaseMap.Title
import androidx.compose.ui.graphics.vector.ImageVector
import java.io.FileDescriptor

data class MenuItem(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val contentDescription: String
)