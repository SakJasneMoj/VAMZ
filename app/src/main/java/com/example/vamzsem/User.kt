package com.example.vamzsem

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int? = null,
    val userName: String,
    val userSurname: String
)
