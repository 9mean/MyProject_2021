package com.example.todoproject

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Plan::class],version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun planDao():PlanDAO
}