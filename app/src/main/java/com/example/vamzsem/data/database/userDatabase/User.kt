package com.example.vamzsem.data.database.userDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int? = null,
    val userName: String,
    val userSurname: String
)
