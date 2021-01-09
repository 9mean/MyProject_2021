package com.example.myproject_2021

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoDao {
    @Query("SELECT * FROM Todo")
    fun getAll():LiveData<List<Todo>>
    @Insert
    suspend fun insert(todo:Todo)
    @Delete
    suspend fun delete(todo: Todo)
}