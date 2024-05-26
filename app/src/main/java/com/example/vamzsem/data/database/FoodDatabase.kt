package com.example.vamzsem.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.vamzsem.ui.utils.Converters
import com.example.vamzsem.data.database.timerDatabase.TimerDao
import com.example.vamzsem.data.database.timerDatabase.TimerEntity
import com.example.vamzsem.data.database.userDatabase.User
import com.example.vamzsem.data.database.userDatabase.UserDao
import com.example.vamzsem.data.database.foodDatabase.Food
import com.example.vamzsem.data.database.foodDatabase.FoodDao

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
