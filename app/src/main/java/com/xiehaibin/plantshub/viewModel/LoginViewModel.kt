package com.xiehaibin.plantshub.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.xiehaibin.plantshub.model.CheckLogin
import com.xiehaibin.plantshub.model.data.CommonData

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private var shpName: String = "user_info"
    private val shp: SharedPreferences =
        getApplication<Application>().getSharedPreferences(shpName, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = shp.edit()

    val accountValue: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val passwordValue: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun checkLogin(callback: (res: Boolean, msg: String?) -> Unit) {
        val account = accountValue.value
        val password = passwordValue.value
        // 获取值失败
        if (account.isNullOrBlank() or password.isNullOrBlank()) {
            return callback(false, "获取值失败")
        }

        // 获取url
        val url: String = CommonData.getInstance().getChkLoginUrl()
        val checkLogin = CheckLogin()
        // 登录验证
        checkLogin.post(
            account.toString(),
            password.toString(),
            url,
            fun(resBoolean, err_code, msg) {
                if (resBoolean) {
                    // 网络请求成功
                    return if (err_code == 0) { // 保存AccountToken
                        setAccountToken(msg)
                        callback(true,null)
                    } else {
                        callback(false, msg)
                    }
                } else {
                    // 网络请求失败
                    callback(false, msg)
                }
            })
    }

    fun setAccountToken (accountToken:String) {
        editor.putString("AccountToken",accountToken)
        editor.apply()
        CommonData.getInstance().setAccountToken(accountToken)
    }
}
