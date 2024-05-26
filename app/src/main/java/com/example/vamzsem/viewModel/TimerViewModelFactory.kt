package com.example.vamzsem.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vamzsem.data.repository.FoodRepository
import com.example.vamzsem.data.repository.TimerRepository

class TimerViewModelFactory(
    private val application: Application,
    private val timerRepository: TimerRepository,
    private val foodRepository: FoodRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TimerViewModel(timerRepository, foodRepository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
