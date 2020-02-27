package com.xiehaibin.plantshub.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.viewModel.MainViewModel
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val err_code = intent?.getIntExtra("err_code",400)
        if (err_code != 400) {
            toast("${err_code}")
        }

    }
}
