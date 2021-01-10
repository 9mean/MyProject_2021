package com.example.myproject_2021

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application : Application): AndroidViewModel(application) {
    var todos:LiveData<List<Todo>>
    var newTodo:String?=null
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "database-name"
    ).fallbackToDestructiveMigration()
            .build()

    init {
        todos=getAll()
    }
    fun getAll():LiveData<List<Todo>>{
        return db.todoDao().getAll()
    }
    fun insert(todo: String){
        viewModelScope.launch(Dispatchers.IO) {
            db.todoDao().insert(Todo(null,todo))
        }
    }

}