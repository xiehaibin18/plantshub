package com.xiehaibin.plantshub.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.xiehaibin.plantshub.model.GetLocationData
import com.xiehaibin.plantshub.model.GetOverviewData
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.model.data.LocationDataItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class OverviewViewModel(application: Application) : AndroidViewModel(application) {
    val overviewData: MutableLiveData<List<Any>> by lazy {
        MutableLiveData<List<Any>>()
    }

    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getOverviewData() {
        // 获取url
        val url: String = CommonData.getInstance().getOverviewDataUrl()
        val getOverviewData = GetOverviewData()
        doAsync {
            getOverviewData.post(
                "allPlants",
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
