package com.example.todoproject

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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