package com.example.todoproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.todoproject.databinding.FragmentHomeBinding
import com.example.todoproject.databinding.FragmentUserBinding

class HomeFragment : Fragment() {
    private var binding:FragmentHomeBinding?=null
    private val REQUEST_PLAN=10
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_PLAN->{
                when(resultCode){
                    100->{
                        val hour=data?.getIntExtra("hour",1)
                        val min=data?.getIntExtra("min",1)
                        Toast.makeText(context, "$hour $min", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}