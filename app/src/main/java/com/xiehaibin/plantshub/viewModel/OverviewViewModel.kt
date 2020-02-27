package com.xiehaibin.plantshub.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.xiehaibin.plantshub.model.GetLocationData
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.model.data.LocationDataItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class OverviewViewModel(application: Application) : AndroidViewModel(application) {
    val locationData: MutableLiveData<List<LocationDataItem>> by lazy {
        MutableLiveData<List<LocationDataItem>>()
    }

    val messsge: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getLocationData() {
        // 获取url
        val url: String = CommonData.getInstance().getLocationDataUrl()
        val getLocationData = GetLocationData()
        doAsync {
            getLocationData.post(
                "2",
                url,
                fun(err_code, msg, data) {
                    uiThread {
                        if (err_code == 0) {
                            locationData.value = data
                        } else {
                            messsge.value = msg
                        }
                    }
                }
            )
        }
    }
}
