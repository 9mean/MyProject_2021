package com.example.todoproject.home.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Plan(
    @PrimaryKey(autoGenerate = true) var id:Int?,
    var title:String,
    var startHour:Int,
    var startMin:Int,
    var endHour:Int,
    var endMin:Int,
    var flag:Int=0
)