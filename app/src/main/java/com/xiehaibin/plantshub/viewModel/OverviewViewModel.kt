package com.xiehaibin.plantshub.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.xiehaibin.plantshub.model.GetListData
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

    val search: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getOverviewData() {
        // 获取url
        var url: String = CommonData.getInstance().getOverviewDataUrl()
        val getOverviewData = GetOverviewData()
        val getListData = GetListData()
        val _search = search.value
        if (_search === "null") {
            doAsync {
                println(11111111111111111)
                getOverviewData.post(
                    CommonData.getInstance().getOverviewDataType(),
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
        } else if (_search == "") {
            message.value = "请输入内容"
        } else if (_search !== "null" && _search !== "") {
            println(_search)
            doAsync {
                getListData.post(
                    "myLocationPlants",
                    _search,
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
}
