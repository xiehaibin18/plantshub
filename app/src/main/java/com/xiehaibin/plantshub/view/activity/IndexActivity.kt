package com.xiehaibin.plantshub.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.model.CheckAccountToken
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.util.ThreadUtil
import com.xiehaibin.plantshub.viewModel.IndexViewModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class IndexActivity : AppCompatActivity() {

    private val viewModel: IndexViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)
        // 观察状态码变化
        val statusCodeObserver = Observer<Int> {
            // status_code: 0验证成功，1无，2数据出错，3网络请求失败,4本地无AccountToken
            when (it) {
                0 -> {
                    startActivity<MainActivity>()
                    finish()
                }
                1, 2, 4 -> {
                    startActivity<LoginActivity>()
                    finish()
                }
                3 -> {
                    toast(viewModel.message.value ?: "验证失败")
                    startActivity<MainActivity>()
                    finish()
                }
            }
        }
        viewModel.status_code.observe(this, statusCodeObserver)
        // 联网验证AccountToken
        viewModel.checkWebAccountToken()
        // 异步操作结束
//        android.os.Handler().postDelayed(Runnable {
//            startActivity<MainActivity>()
//            finish()
//        },100)
    }
}