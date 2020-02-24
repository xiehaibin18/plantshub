package com.xiehaibin.plantshub.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.xiehaibin.plantshub.R

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

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

    fun clickRegisterSubmitButton() {

        val accountRegex = Regex(getApplication<Application>().getString(R.string.app_accountRegex))
        val passwordRegex = Regex(getApplication<Application>().getString(R.string.app_passwordRegex))
        val pwdcheckRegex = Regex(getApplication<Application>().getString(R.string.app_passwordRegex))
        val phoneRegex = Regex(getApplication<Application>().getString(R.string.app_phoneRegex))
        val nicknameRegex = Regex(getApplication<Application>().getString(R.string.app_nicknameRegex))

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
            phoneErrorHint.value === null){
            accountValue.value = ""
        }
    }
}
