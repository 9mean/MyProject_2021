package com.example.todoproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Plan(
    @PrimaryKey(autoGenerate = true) var id:Int?,
    var title:String
)