package com.xiehaibin.plantshub.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    val accountValue: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val passwordValue: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val pwdcheckValue: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val nicknameValue: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val phoneValue: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}
