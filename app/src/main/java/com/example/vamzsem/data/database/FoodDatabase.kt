package com.example.vamzsem.food_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.vamzsem.Converters
import com.example.vamzsem.TimerDao
import com.example.vamzsem.TimerEntity
import com.example.vamzsem.User
import com.example.vamzsem.UserDao

@Database(entities = [Food::class, User::class, TimerEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun userDao(): UserDao
    abstract fun timerDao(): TimerDao

    companion object {
        @Volatile
        private var INSTANCE: FoodDatabase? = null

        fun getDatabase(context: android.content.Context): FoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java,
                    "food_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
