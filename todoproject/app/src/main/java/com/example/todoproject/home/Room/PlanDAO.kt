package com.example.todoproject.home.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.todoproject.home.Room.Plan

@Dao
interface PlanDAO {
    @Query("SELECT * FROM `Plan`")
    fun getAll(): LiveData<List<Plan>>
    @Insert
    suspend fun insert(todo: Plan)
    @Delete
    suspend fun delete(todo: Plan)
}