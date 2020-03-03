package com.xiehaibin.plantshub.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val info = intent?.getBundleExtra("info")
//        if (info?.getInt("goto", 0) == 100) {
//            findNavController().navigate(R.id.action_homeFragment_to_userLoggedFragment)
//        }

    }
}
