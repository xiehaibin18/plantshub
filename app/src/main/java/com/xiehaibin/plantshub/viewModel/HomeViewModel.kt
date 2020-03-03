package com.xiehaibin.plantshub.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.xiehaibin.plantshub.model.GetListData
import com.xiehaibin.plantshub.model.data.AllPlantsDataItem
import com.xiehaibin.plantshub.model.data.CommonData
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    val overviewData: MutableLiveData<List<AllPlantsDataItem>> by lazy {
        MutableLiveData<List<AllPlantsDataItem>>()
    }
    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    fun getData() {
        // 获取url
        val url: String = CommonData.getInstance().getListDataUrl()
        val getListData = GetListData()
        doAsync {
            getListData.post(
                "homeHot",
                "",
                url,
                fun(err_code, msg, data) {
                    uiThread {
                        if (err_code == 0) {
                            overviewData.value = data
                        } else {
                            message.value = msg
                        }
                    }
                }
            )
        }
    }
}
