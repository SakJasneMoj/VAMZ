package com.example.vamzsem

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FoodViewModelFactory(
    private val foodRepository: FoodRepository,
    private val userRepository: UserRepository,
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodViewModel::class.java)) {
            return FoodViewModel(foodRepository, userRepository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
