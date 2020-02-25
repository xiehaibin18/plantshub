package com.xiehaibin.plantshub.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.model.CheckAccountToken
import com.xiehaibin.plantshub.model.data.CommonData
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class IndexViewModel(application: Application) : AndroidViewModel(application) {
    var defValue = application.resources.getString(R.string.app_defValue)
    private var shpName: String = "user_info"
    private val shp: SharedPreferences =
        getApplication<Application>().getSharedPreferences(shpName, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = shp.edit()

    // status_code: 0验证成功，1无，2数据出错，3网络请求失败,4本地无AccountToken
    val status_code: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    // message：返回消息
    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    // 验证本地AccountToken是否为空
    private fun checkLocalAccountToken(): Boolean = shp.contains("AccountToken")

    // 获取本地AccountToken
    private fun getLocalAccountToken(): String = shp.getString("AccountToken", defValue)

    private fun setAccountToken(accountToken: String) {
        editor.putString("AccountToken", accountToken)
        editor.apply()
        CommonData.getInstance().setAccountToken(accountToken)
    }

    fun checkWebAccountToken() {
        // 验证本地AccountToken,有则验证，无则跳转登录界面
        if (checkLocalAccountToken()) {
            // 获取本地验证本地AccountToken
            val accountToken: String = getLocalAccountToken()
            // 获取url
            val url: String = CommonData.getInstance().getChkAccountTokenUrl()
            val checkAccountToken = CheckAccountToken()
            // 获取验证结果
            doAsync {
                checkAccountToken.post(accountToken, url, fun(err_code, msg) {
                    // err_code: 0验证成功，1无，2数据出错，3网络请求失败，400数据传输失败,500服务器出错
                    uiThread {
                        if (err_code == 0) {
                            setAccountToken(accountToken)
                        }
                        message.value = msg
                        status_code.value = err_code
                    }
                })
            }
        } else {
            status_code.value = 4
        }
    }
}
