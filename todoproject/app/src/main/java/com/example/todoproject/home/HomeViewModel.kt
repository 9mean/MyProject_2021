package com.example.todoproject.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.todoproject.home.Room.AppDatabase
import com.example.todoproject.home.Room.Plan
import java.util.*

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
    fun checkStartPlan(){
        val calendar: Calendar = Calendar.getInstance()
        plans.value!!.forEach {
            if(it.startHour==calendar[Calendar.HOUR_OF_DAY] && it.startMin==calendar[Calendar.MINUTE])
            {
                it.flag=1
            }
        }
        return
    }
    suspend fun insert(todo: Plan){
        db.planDao().insert(todo)
    }
    suspend fun delete(todo:Plan){
        db.planDao().delete(todo)
    }
}