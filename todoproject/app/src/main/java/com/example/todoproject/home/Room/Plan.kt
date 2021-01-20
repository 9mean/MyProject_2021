package com.example.todoproject.home.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Plan(
    @PrimaryKey(autoGenerate = true) var id:Int?,
    var title:String
)