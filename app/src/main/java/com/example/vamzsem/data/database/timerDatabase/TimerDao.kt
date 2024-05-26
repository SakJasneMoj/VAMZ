package com.example.vamzsem.data.database.timerDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import com.example.vamzsem.data.database.timerDatabase.TimerEntity

@Dao
interface TimerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(timerEntity: TimerEntity)

    @Delete
    suspend fun delete(timerEntity: TimerEntity)

    @Query("SELECT * FROM timer_entity WHERE date = :date")
    suspend fun getTimersForDate(date: String): List<TimerEntity>

    @Query("SELECT SUM(timeSpent) FROM timer_entity WHERE date = :date")
    suspend fun getTotalTimeSpentForDate(date: String): Long?



}
