package com.example.todoproject.home

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.todoproject.LoginActivity
import com.example.todoproject.R
import com.example.todoproject.home.Room.Plan
import kotlinx.coroutines.launch

class HomeAdapter(val mviewModel: HomeViewModel, val viewLifecycleOwner: LifecycleOwner):RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.plan_list,parent,false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return HomeFragment.plan.value!!.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        //holder.title.text=plan[position].title
        HomeFragment.plan.value!![position].let { item ->
            with(holder) {
                title.text = item.title
                startTime.text = "시작시간 : "+item.startHour.toString()+"시 "+item.startMin.toString()+"분"
                endTime.text = "종료시간 : "+item.endHour.toString()+"시 "+item.endMin.toString()+"분"
                if(item.flag==1){
                    startTime.setTextColor(Color.RED)
                }
            }
        }
        //삭제버튼누르면 삭제
        holder.deleteBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                mviewModel.delete(HomeFragment.plan.value!![position])
            }
        }

    }
    inner class HomeViewHolder(planView: View) : RecyclerView.ViewHolder(planView) {
        val title=planView.findViewById<TextView>(R.id.plan_title)
        val startTime=planView.findViewById<TextView>(R.id.plan_start_time)
        val endTime=planView.findViewById<TextView>(R.id.plan_end_time)
        val deleteBtn=planView.findViewById<Button>(R.id.plan_delete_btn)
    }
}