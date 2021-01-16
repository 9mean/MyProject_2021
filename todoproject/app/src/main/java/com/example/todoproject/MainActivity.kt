package com.example.todoproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.todoproject.databinding.ActivityLoginBinding
import com.example.todoproject.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener{
    private  var fhome: Fragment?=null
    private  var fsearch: Fragment?=null
    private  var fnotification: Fragment?=null
    private  var fuser: Fragment?=null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)
        //트랜잭션
        fhome=HomeFragment()
        supportFragmentManager.beginTransaction().add(R.id.main_frame, fhome!!).setTransition(
            FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
        Log.d("TAG","메인액티비티 onCreate")
    }
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.action_home ->{
                if(fhome==null){
                    fhome=HomeFragment()
                    supportFragmentManager.beginTransaction().add(R.id.main_frame,fhome!!).setTransition(
                        FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
                    Log.d("TAG","메인액티비티 home null->create")
                }
                if(fhome!=null) supportFragmentManager.beginTransaction().show(fhome!!).commit()
                if(fsearch!=null) supportFragmentManager.beginTransaction().hide(fsearch!!).commit()
                if(fuser!=null) supportFragmentManager.beginTransaction().hide(fuser!!).commit()
                if(fnotification!=null) supportFragmentManager.beginTransaction().hide(fnotification!!).commit()
            }
            R.id.action_search -> {
                if(fsearch==null){
                    Log.d("TAG","메인액티비티 search null->create")
                    fsearch=SearchFragment()
                    supportFragmentManager.beginTransaction().add(R.id.main_frame,fsearch!!).setTransition(
                        FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
                }
                if(fhome!=null) supportFragmentManager.beginTransaction().hide(fhome!!).commit()
                if(fsearch!=null) supportFragmentManager.beginTransaction().show(fsearch!!).commit()
                if(fuser!=null) supportFragmentManager.beginTransaction().hide(fuser!!).commit()
                if(fnotification!=null) supportFragmentManager.beginTransaction().hide(fnotification!!).commit()
            }
            R.id.action_notification -> {
                if(fnotification==null){
                    Log.d("TAG","메인액티비티 room null->create")
                    fnotification=NotificationFragment()
                    supportFragmentManager.beginTransaction().add(R.id.main_frame,fnotification!!).setTransition(
                        FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
                }
                if(fhome!=null) supportFragmentManager.beginTransaction().hide(fhome!!).commit()
                if(fsearch!=null) supportFragmentManager.beginTransaction().hide(fsearch!!).commit()
                if(fuser!=null) supportFragmentManager.beginTransaction().hide(fuser!!).commit()
                if(fnotification!=null) supportFragmentManager.beginTransaction().show(fnotification!!).commit()
            }
            R.id.action_user -> {
                if(fuser==null){
                    Log.d("TAG","메인액티비티 user null->create")
                    fuser=UserFragment()
                    supportFragmentManager.beginTransaction().add(R.id.main_frame,fuser!!).setTransition(
                        FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
                }
                if(fhome!=null) supportFragmentManager.beginTransaction().hide(fhome!!).commit()
                if(fsearch!=null) supportFragmentManager.beginTransaction().hide(fsearch!!).commit()
                if(fuser!=null) supportFragmentManager.beginTransaction().show(fuser!!).commit()
                if(fnotification!=null) supportFragmentManager.beginTransaction().hide(fnotification!!).commit()
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG","메인액티비티 onDestroy")
    }
    //뒤로가기 두번연속 누를경우 종료시키기
    private val FINISH_INTERVAL_TIME:Long= 2000
    private var backPressedTime:Long=0
    override fun onBackPressed() {

        if(supportFragmentManager.backStackEntryCount == 0) {
            var tempTime = System.currentTimeMillis();
            var intervalTime = tempTime - backPressedTime;
            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                super.onBackPressed();
            } else {
                backPressedTime = tempTime;
                Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
                return
            }
        }
        super.onBackPressed()
    }



}