package com.example.todoproject.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.todoproject.R
import com.example.todoproject.home.Room.Plan

class HomeAdapter(val plan: MutableLiveData<ArrayList<Plan>>):RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.plan_list,parent,false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return plan.value!!.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        //holder.title.text=plan[position].title
        plan.value!![position].let { item ->
            with(holder) {
                title.text = item.title
                //.text = item.nickname
            }
        }
    }
    inner class HomeViewHolder(planView: View) : RecyclerView.ViewHolder(planView) {
        val title=planView.findViewById<TextView>(R.id.plan_title)
        val startTime=planView.findViewById<TextView>(R.id.plan_start_time)
        val endTime=planView.findViewById<TextView>(R.id.plan_end_time)
    }
}