package com.example.vamzsem

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete

@Dao
interface TimerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(timerEntity: TimerEntity)

    @Delete
    suspend fun delete(timerEntity: TimerEntity)

    @Query("SELECT * FROM timer_entity WHERE date = :date")
    suspend fun getTimersForDate(date: String): List<TimerEntity>
}
