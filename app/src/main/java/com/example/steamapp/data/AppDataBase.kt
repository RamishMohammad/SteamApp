package com.example.steamapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import java.util.*

@Database(entities = [User::class], version = 2)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
