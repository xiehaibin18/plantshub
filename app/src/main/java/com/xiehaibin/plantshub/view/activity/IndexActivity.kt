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
                    val intent: Intent = Intent()
                    intent.setClass(this, MainActivity::class.java)
//                    var bundle: Bundle = Bundle()
//                    bundle.putString("msg", "123")
//                    intent.putExtras(bundle)
                    startActivity(intent)
                    finish()
                }
            }
        }
        viewModel.status_code.observe(this, statusCodeObserver)
        viewModel.checkWebAccountToken()
        // 异步操作结束
//        android.os.Handler().postDelayed(Runnable {
//            startActivity<MainActivity>()
//            finish()
//        },100)
        // volley demo
//        val queue = Volley.newRequestQueue(this)
//        val baseUrl: String = "http://10.0.2.2:3000"
//        var url: String = "/api/test"
//        val stringRequest = StringRequest(
//            Request.Method.GET,
//            baseUrl.plus(url),
//            Response.Listener<String> { response ->
//                viewModel.resData.value = response
//            },
//            Response.ErrorListener {
//                viewModel.resData.value = "error"
//            }
//        )
//        queue.add(stringRequest)
    }
}