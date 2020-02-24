package com.xiehaibin.plantshub.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.model.CheckLogin
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.view.activity.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

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

    val accountErrorHint: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val passwordErrorHint: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val submitButtonIsEnabled: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val checkLoginStatus: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val checkLoginMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private fun checkLogin(callback: (res: Boolean, msg: String?) -> Unit) {
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
                        this.setAccountToken(msg)
                        callback(true, null)
                    } else {
                        callback(false, msg)
                    }
                } else {
                    // 网络请求失败
                    callback(false, msg)
                }
            })
    }

    fun setAccountToken(accountToken: String) {
        editor.putString("AccountToken", accountToken)
        editor.apply()
        CommonData.getInstance().setAccountToken(accountToken)
    }

    fun clickLoginSubmitButton() {

        val accountRegex = Regex(getApplication<Application>().getString(R.string.app_accountRegex))
        if (!accountRegex.matches(accountValue.value ?: "")) {
            accountErrorHint.value =
                getApplication<Application>().getString(R.string.app_login_account_textInputLayout_error)
        } else {
            accountErrorHint.value = null
        }

        val passwordRegex = Regex(getApplication<Application>().getString(R.string.app_passwordRegex))
        if (!passwordRegex.matches(passwordValue.value ?: "")) {
            passwordErrorHint.value =
                getApplication<Application>().getString(R.string.app_login_password_textInputLayout_error)
        } else {
            passwordErrorHint.value = null
        }

        if (accountErrorHint.value === null && passwordErrorHint.value === null) {
            submitButtonIsEnabled.value = false
            doAsync {
                checkLogin(fun(res, msg) {
                    uiThread {
                        checkLoginMessage.value = msg
                        checkLoginStatus.value = res
                    }
                })
            }
        }
    }
}
