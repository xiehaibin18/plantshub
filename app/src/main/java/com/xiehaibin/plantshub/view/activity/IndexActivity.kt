package com.xiehaibin.plantshub.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.viewModel.DetailViewModel
import com.xiehaibin.plantshub.viewModel.IndexViewModel
import org.jetbrains.anko.startActivity

class IndexActivity : AppCompatActivity() {

    private val viewModel: IndexViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)
        // 异步操作结束
        android.os.Handler().postDelayed(Runnable {
            startActivity<MainActivity>()
            finish()
        },3000)
    }


}