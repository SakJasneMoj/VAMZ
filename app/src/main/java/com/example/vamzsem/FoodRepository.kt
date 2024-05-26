package com.example.vamzsem

import androidx.annotation.WorkerThread
import com.example.vamzsem.food_database.Food
import com.example.vamzsem.food_database.FoodDao
import kotlinx.coroutines.flow.Flow

class FoodRepository(private val dao: FoodDao) {




    suspend fun insertFood(food: Food) {
        dao.insertFood(food)
    }

    suspend fun deleteFood(food: Food) {
        dao.deleteFood(food)
    }

    fun getFoodByUser(userId: String): Flow<List<Food>> {
        return dao.getFoodByUser(userId)
    }

    fun getFoodByUserAndDate(userId: String, date: String): Flow<List<Food>> {
        return dao.getFoodByUserAndDate(userId, date)
    }

    fun getTotalCaloriesByUserAndDate(userId: String, date: String): Flow<Int> {
        return dao.getTotalCaloriesByUserAndDate(userId, date)
    }

    fun getFoodsForDate(date: String): List<Food> {
        return dao.getFoodsForDate(date)
    }


}
