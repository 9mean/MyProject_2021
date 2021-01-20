package com.example.todoproject.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.todoproject.home.Room.AppDatabase
import com.example.todoproject.home.Room.Plan

class HomeViewModel(application : Application): AndroidViewModel(application){
    var plans:LiveData<List<Plan>>
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "database-name"
    ).fallbackToDestructiveMigration()
        .build()
    init {
        plans=getAll()
    }
    fun getAll():LiveData<List<Plan>>{
        return db.planDao().getAll()
    }
    suspend fun insert(todo: Plan){
        db.planDao().insert(todo)
    }
}