package com.example.vamzsem.food_database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val userId: String, // User identifier
    val nameOfFood: String,
    val typeOfFood: String,
    val calories: Int, // Change to Int for easier calculations
    val date: String // Date when the food was added
)
