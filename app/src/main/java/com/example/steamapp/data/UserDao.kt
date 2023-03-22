package com.example.steamapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*


@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>

    @Insert
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun getUserByUsernameAndPassword(username: String, password: String): User?
}