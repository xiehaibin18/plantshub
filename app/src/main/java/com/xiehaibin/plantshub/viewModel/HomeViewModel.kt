package com.xiehaibin.plantshub.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    // 底部导航栏按钮状态
    val userIsEnable:MutableLiveData<Boolean> by lazy {
            MutableLiveData<Boolean>(false)
    }
    val indexIsEnable:MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(true)
    }
    val studyIsEnable:MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
}
