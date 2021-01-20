package com.example.todoproject.home

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.TimePicker
import android.widget.Toast
import com.example.todoproject.LoginActivity
import com.example.todoproject.databinding.ActivityPlanBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.math.min

class PlanActivity : AppCompatActivity(),TimePicker.OnTimeChangedListener{
    private lateinit var binding: ActivityPlanBinding
    private var shour=0
    private var smin=0
    private var ehour=0
    private var emin=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.startTimePicker.setOnTimeChangedListener(this)
        binding.endTimePicker.setOnTimeChangedListener(this)
        binding.timeOkBtn.setOnClickListener {
            if (HomeFragment.plan.value!!.size==3){
                Toast.makeText(this, "오늘 계획을 초과하셨어요", Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this, HomeFragment::class.java)
                intent.putExtra("shour", shour)
                intent.putExtra("smin", smin)
                intent.putExtra("ehour", ehour)
                intent.putExtra("emin", emin)
                intent.putExtra("title", binding.planTitle.text.toString())
                setResult(100, intent)
                finish()
            }
        }
    }

    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Log.d("TAG", "$hourOfDay $minute")
        when (view){
            binding.startTimePicker->{
                smin=minute
                shour=hourOfDay
            }
            binding.endTimePicker->{
                emin=minute
                ehour=hourOfDay
            }
        }

    }

}