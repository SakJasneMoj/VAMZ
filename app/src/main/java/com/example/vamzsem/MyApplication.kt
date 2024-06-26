package com.example.vamzsem

import android.app.Application
import androidx.room.Room
import com.example.vamzsem.data.repository.FoodRepository
import com.example.vamzsem.data.repository.TimerRepository
import com.example.vamzsem.data.database.FoodDatabase
import com.example.vamzsem.data.database.userDatabase.UserRepository

class MyApplication : Application() {

    lateinit var database: FoodDatabase
        private set

    lateinit var foodRepository: FoodRepository
        private set

    lateinit var userRepository: UserRepository
        private set

    lateinit var timerRepository: TimerRepository
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            FoodDatabase::class.java,
            "food_database"
        ).build()

        foodRepository = FoodRepository(database.foodDao())
        userRepository = UserRepository(database.userDao())
        timerRepository = TimerRepository(database.timerDao(), applicationContext)
    }
}
