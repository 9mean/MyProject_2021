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

            //
//            val calendar=Calendar.getInstance()
//            calendar.set(Calendar.MINUTE, mmin)
//            calendar.set(Calendar.HOUR_OF_DAY, mhour)
//            calendar.set(Calendar.SECOND, 0)
//            //알람매니저
//            val alarmManager = getSystemService(ALARM_SERVICE) as? AlarmManager
//            val intentreceiver = Intent(this, AlarmReceiver::class.java)  // 1
//            val pendingIntent = PendingIntent.getBroadcast(     // 2
//                    this, AlarmReceiver.NOTIFICATION_ID, intentreceiver,
//                    PendingIntent.FLAG_UPDATE_CURRENT)
//                alarmManager?.set(   // 5
//                        AlarmManager.RTC_WAKEUP,
//                        calendar.timeInMillis,
//                        pendingIntent
//                )
//            Log.d(LoginActivity.TAG, "alarm ${calendar.time}")
//            runBlocking {
//                delay(2000)
//            }
            val intent = Intent(this, HomeFragment::class.java)
            intent.putExtra("hour", mhour)
            intent.putExtra("min", mmin)
            intent.putExtra("title", binding.planTitle.text.toString())
            setResult(100, intent)
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