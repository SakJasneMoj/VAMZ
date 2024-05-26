package com.example.vamzsem.ui.components

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class CalorieCounterViewModel : ViewModel() {
    var calories by mutableStateOf(0)
        private set

    fun incrementCalories() {
        calories++
    }

    fun decrementCalories() {
        if (calories > 0) {
            calories--
        }
    }
}
