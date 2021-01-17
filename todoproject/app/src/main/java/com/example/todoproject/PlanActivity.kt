package com.example.todoproject

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import com.example.todoproject.databinding.ActivityPlanBinding
import java.util.*

class PlanActivity : AppCompatActivity(),TimePicker.OnTimeChangedListener{
    private lateinit var binding: ActivityPlanBinding
    private var mhour=0
    private var mmin=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.timePicker.
//        val cal=Calendar.getInstance()
//       // binding.timeRangePicker.setStartTime(cal)
//        Log.d("TAG", "onCreate: ${cal}")
//        val tp=binding.timePicker
//        tp.setOnTimeChangedListener(this,)
        //var tpd=TimePickerDialog(this,this,1,1,true)
        binding.timePicker.setOnTimeChangedListener(this)
        binding.timeOkBtn.setOnClickListener {
            val intent= Intent(this,HomeFragment::class.java)
            intent.putExtra("hour",mhour)
            intent.putExtra("min",mmin)
            intent.putExtra("title",binding.planTitle.text.toString())
            setResult(100,intent)
            finish()
        }
    }

//    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
//        Log.d("TAG", "$hourOfDay $minute")
//    }

    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Log.d("TAG", "$hourOfDay $minute")
        mmin=minute
        mhour=hourOfDay
    }

}