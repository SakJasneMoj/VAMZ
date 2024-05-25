package com.example.vamzsem.food_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.vamzsem.Converters
import com.example.vamzsem.User
import com.example.vamzsem.UserDao

@Database(
    entities = [Food::class, User::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class FoodDatabase : RoomDatabase() {
    abstract val dao: FoodDao
    abstract val userDao: UserDao
}
