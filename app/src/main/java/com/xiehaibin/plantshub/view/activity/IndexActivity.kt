package com.xiehaibin.plantshub.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
        // 子线程执行
        doAsync {
            viewModel.checkWebAccountToken(fun(res: Int?, msg: String?) {
                when (res) {
                    0 -> {
                        startActivity<MainActivity>()
                        finish()
                    }
                    1 -> {
                        uiThread {
                            toast("请确保在有网络的情况下使用，${msg}")
                        }
                    }
                    2 -> {
                        startActivity<LoginActivity>()
                        finish()
                    }
                }
            })
        }
//        doAsync {
//            // 验证本地AccountToken,有则验证，无则跳转登录界面
//            if (viewModel.checkLocalAccountToken()) {
//                // 获取本地验证本地AccountToken
//                val accountToken: String = viewModel.getLocalAccountToken()
//                // 获取url
//                val url: String = CommonData.getInstance().getChkAccountTokenUrl()
//                var checkAccountToken = CheckAccountToken()
//                // 获取验证结果
//                checkAccountToken.getBoolean(accountToken, url, fun(res: Boolean, err: String) {
//                    if (res) {
//                        startActivity<MainActivity>()
//                        finish()
//                    } else {
//                        uiThread {
//                            toast("请确保在有网络的情况下使用，${err}")
//                        }
//                    }
//                })
//            } else {
//                startActivity<LoginActivity>()
//                finish()
//            }
//        }
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