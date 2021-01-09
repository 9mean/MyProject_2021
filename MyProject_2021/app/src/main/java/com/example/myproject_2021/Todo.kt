package com.example.myproject_2021

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true) var id:Int?,
    val title:String
)

