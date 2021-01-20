package com.example.todoproject.home

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoproject.LoginActivity
import com.example.todoproject.LoginActivity.Companion.TAG
import com.example.todoproject.databinding.FragmentHomeBinding
import com.example.todoproject.home.Room.Plan
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private var binding:FragmentHomeBinding?=null
    private val REQUEST_PLAN=10
    private val mViewModel: HomeViewModel by viewModels()
    lateinit var homeAdapter: HomeAdapter
    var plans = MutableLiveData<ArrayList<Plan>>()
    var hour=1
    var min=1
    var planTitle=""
    override fun onDestroyView() {
        binding=null

        super.onDestroyView()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(layoutInflater,container,false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.homeRecyclerView.layoutManager=LinearLayoutManager(context)
        binding!!.homePlanPlusBtn.setOnClickListener {
            startActivityForResult(Intent(context,
                PlanActivity::class.java),REQUEST_PLAN)
        }
        mViewModel.plans.observe(viewLifecycleOwner, Observer {
            plans.value= it as ArrayList<Plan>?
                binding!!.homeRecyclerView.adapter=HomeAdapter(plans)
            Log.d(TAG, "onViewCreated: observing $it")
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_PLAN->{
                when(resultCode){
                    100->{
                        hour=data!!.getIntExtra("hour",1)
                        min=data.getIntExtra("min",1)
                        planTitle= data.getStringExtra("title")!!
                        Toast.makeText(context, "$hour $min $planTitle", Toast.LENGTH_SHORT).show()
//                        lifecycleScope.launch {
//                            mViewModel.insert(
//                                Plan(
//                                    null,
//                                    planTitle
//                                )
//                            )
//                        }
                        val calendar: Calendar = Calendar.getInstance().apply {
                            timeInMillis = System.currentTimeMillis()
                            set(Calendar.MINUTE, min)
                            set(Calendar.HOUR_OF_DAY, hour)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }
                        //알람매니저
                        val alarmManager = context?.getSystemService(ALARM_SERVICE) as? AlarmManager
                        val intent = Intent(context, AlarmReceiver::class.java)  // 1
                        val pendingIntent = PendingIntent.getBroadcast(     // 2
                                context, AlarmReceiver.NOTIFICATION_ID, intent,
                                PendingIntent.FLAG_UPDATE_CURRENT)
                        setAlarmWithAPI(alarmManager!!,calendar.timeInMillis,pendingIntent)
                        Log.d(TAG, "alarm ${calendar.time}")
                    }
                }
            }
        }
    }
    fun setAlarmWithAPI(am:AlarmManager,firstTime:Long,sender:PendingIntent){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //API 19 이상 API 23미만
                am.setExact(AlarmManager.RTC_WAKEUP, firstTime, sender) ;
            } else {
                //API 19미만
                am.set(AlarmManager.RTC_WAKEUP, firstTime, sender);
            }
        } else {
            //API 23 이상
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, firstTime, sender);
        }
    }

}