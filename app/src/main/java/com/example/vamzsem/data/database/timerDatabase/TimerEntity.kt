package com.example.vamzsem

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer_entity")
data class TimerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val activityName: String,
    val timeSpent: Long,
    val date: String = ""
)
