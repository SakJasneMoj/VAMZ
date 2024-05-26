package com.example.vamzsem.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vamzsem.data.database.userDatabase.UserRepository
import com.example.vamzsem.data.repository.FoodRepository

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
