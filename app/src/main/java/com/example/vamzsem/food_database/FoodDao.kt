package com.example.vamzsem.food_database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface FoodDao {
    @Insert
    suspend fun insertFood(food: Food)


    @Delete
    suspend fun deleteFood(food: Food)

    @Query("SELECT * FROM food WHERE userId = :userId ORDER BY date DESC")
    fun getFoodByUser(userId: String): Flow<List<Food>>

    @Query("SELECT * FROM food WHERE userId = :userId AND date = :date ORDER BY calories")
    fun getFoodByUserAndDate(userId: String, date: String): Flow<List<Food>>

    @Query("SELECT SUM(calories) FROM food WHERE userId = :userId AND date = :date")
    fun getTotalCaloriesByUserAndDate(userId: String, date: String): Flow<Int>
}
