package com.example.myproject_2021

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Todo::class],version = 2)
abstract class AppDatabase:RoomDatabase() {
    abstract fun todoDao():TodoDao
}