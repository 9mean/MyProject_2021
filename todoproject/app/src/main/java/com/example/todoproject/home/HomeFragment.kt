package com.example.todoproject.home

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoproject.LoginActivity.Companion.TAG
import com.example.todoproject.databinding.FragmentHomeBinding
import com.example.todoproject.home.Room.Plan
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private var binding:FragmentHomeBinding?=null
    private val REQUEST_PLAN=10
    private val mViewModel: HomeViewModel by viewModels()
    var shour=1
    var smin=1
    var ehour=1
    var emin=1
    var planTitle=""
    companion object{
        var plan = MutableLiveData<ArrayList<Plan>>()
    }
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
            plan.value= it as ArrayList<Plan>?
            binding!!.homeRecyclerView.adapter=HomeAdapter(mViewModel,viewLifecycleOwner)
            Log.d(TAG, "onViewCreated: observing $it")
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_PLAN->{
                when(resultCode){
                    //100이면 플랜액티에서 응답옴
                    100->{
                        shour=data!!.getIntExtra("shour",1)
                        smin=data.getIntExtra("smin",1)
                        ehour=data!!.getIntExtra("ehour",1)
                        emin=data.getIntExtra("emin",1)
                        planTitle= data.getStringExtra("title")!!
                        lifecycleScope.launch {
                            mViewModel.insert(
                                Plan(null, planTitle, shour, smin,ehour,emin,0)
                            )
                        }
                        val calendar: Calendar = Calendar.getInstance().apply {
                            timeInMillis = System.currentTimeMillis()
                            set(Calendar.MINUTE, smin)
                            set(Calendar.HOUR_OF_DAY, shour)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }
                        //알람매니저
                        val alarmManager = context?.getSystemService(ALARM_SERVICE) as? AlarmManager
                        val intent = Intent(context, AlarmReceiver::class.java)  // 1
                        intent.putExtra("planTitle",planTitle)
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