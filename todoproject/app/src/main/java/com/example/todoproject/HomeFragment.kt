package com.example.todoproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import com.example.todoproject.databinding.FragmentHomeBinding
import com.example.todoproject.databinding.FragmentUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var binding:FragmentHomeBinding?=null
    private val REQUEST_PLAN=10
    private val mViewModel:HomeViewModel by viewModels()
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
        binding!!.homePlanPlusBtn.setOnClickListener {
            startActivityForResult(Intent(context,PlanActivity::class.java),REQUEST_PLAN)
        }
        mViewModel.plans.observe(viewLifecycleOwner, Observer {
            binding!!.homePlanListTextview.text=it.toString()
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
                        lifecycleScope.launch {
                            mViewModel.insert(Plan(null,planTitle))
                        }
                    }
                }
            }
        }
    }
}