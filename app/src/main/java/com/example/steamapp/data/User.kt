package com.example.steamapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val username: String,
    val password: String
)
