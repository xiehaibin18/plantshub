package com.xiehaibin.plantshub.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getDetailViewData(type: Int, itemUid: Int) {
        if (type != 400 && itemUid != 400) {

        } else {
            message.value = "获取数据失败"
        }
    }
}
