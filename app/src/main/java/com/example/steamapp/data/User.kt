package com.example.steamapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    val username: String,
    @PrimaryKey
    val password: String
)
