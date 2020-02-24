package com.xiehaibin.plantshub.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.model.UserRegister
import com.xiehaibin.plantshub.model.data.CommonData
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private var shpName: String = "user_info"
    private val shp: SharedPreferences =
        getApplication<Application>().getSharedPreferences(shpName, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = shp.edit()

    val accountValue: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val accountErrorHint: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val passwordValue: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val passwordErrorHint: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val pwdcheckValue: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val pwdcheckErrorHint: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val nicknameValue: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val nicknameErrorHint: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val phoneValue: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val phoneErrorHint: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    // status_code: 0验证成功，1无，2数据出错，3网络请求失败,4本地无AccountToken
    val status_code: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    // message：返回消息
    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val submitButtonIsEnabled: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun clickRegisterSubmitButton() {

        val accountRegex = Regex(getApplication<Application>().getString(R.string.app_accountRegex))
        val passwordRegex =
            Regex(getApplication<Application>().getString(R.string.app_passwordRegex))
        val pwdcheckRegex =
            Regex(getApplication<Application>().getString(R.string.app_passwordRegex))
        val phoneRegex = Regex(getApplication<Application>().getString(R.string.app_phoneRegex))
        val nicknameRegex =
            Regex(getApplication<Application>().getString(R.string.app_nicknameRegex))

        if (!accountRegex.matches(accountValue.value ?: "")) {
            accountErrorHint.value =
                getApplication<Application>().getString(R.string.app_account_textInputLayout_error)
        } else {
            accountErrorHint.value = null
        }
        if (!passwordRegex.matches(passwordValue.value ?: "")) {
            passwordErrorHint.value =
                getApplication<Application>().getString(R.string.app_password_textInputLayout_error)
        } else {
            passwordErrorHint.value = null
        }
        if (!pwdcheckRegex.matches(pwdcheckValue.value ?: "")) {
            pwdcheckErrorHint.value =
                getApplication<Application>().getString(R.string.app_password_textInputLayout_error)
        } else {
            pwdcheckErrorHint.value = null
        }
        if (!phoneRegex.matches(phoneValue.value ?: "")) {
            phoneErrorHint.value =
                getApplication<Application>().getString(R.string.app_phone_textInputLayout_error)
        } else {
            phoneErrorHint.value = null
        }
        if (!nicknameRegex.matches(nicknameValue.value ?: "")) {
            nicknameErrorHint.value =
                getApplication<Application>().getString(R.string.app_nickname_textInputLayout_error)
        } else {
            nicknameErrorHint.value = null
        }
        if (!phoneRegex.matches(phoneValue.value ?: "")) {
            phoneErrorHint.value =
                getApplication<Application>().getString(R.string.app_phone_textInputLayout_error)
        } else {
            phoneErrorHint.value = null
        }

        if (accountErrorHint.value === null &&
            passwordErrorHint.value === null &&
            pwdcheckErrorHint.value === null &&
            nicknameErrorHint.value === null &&
            phoneErrorHint.value === null
        ) {
            submitButtonIsEnabled.value = false
            // 获取url
            val url: String = CommonData.getInstance().getUserRegisterUrl()
            val userRegister = UserRegister()
            val account = accountValue.value.toString()
            val password = passwordValue.value.toString()
            val nickname = nicknameValue.value.toString()
            val phone = phoneValue.value.toString()
            // 获取注册结果
            doAsync {
                userRegister.post(account, password, nickname, phone, url, fun(err_code, msg) {
                    // err_code: 0验证成功，1无，2数据出错，3网络请求失败，400数据传输失败
                    uiThread {
                        if (err_code == 0) {
                            setAccountToken(msg)
                        }
                        message.value = msg
                        status_code.value = err_code
                    }
                })
            }
        }
    }

    private fun setAccountToken(accountToken: String) {
        editor.putString("AccountToken", accountToken)
        editor.apply()
        CommonData.getInstance().setAccountToken(accountToken)
    }
}
