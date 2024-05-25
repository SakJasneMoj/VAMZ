package com.example.vamzsem

import android.app.Application
import androidx.room.Room
import com.example.vamzsem.food_database.FoodDatabase

class MyApplication : Application() {
    lateinit var foodRepository: FoodRepository
    lateinit var userRepository: UserRepository

    override fun onCreate() {
        super.onCreate()

        // Initialize the Room database
        val db = Room.databaseBuilder(
            applicationContext,
            FoodDatabase::class.java,
            "food_database"
        ).build()

        foodRepository = FoodRepository(db.dao)
        userRepository = UserRepository(db.userDao)
    }
}
