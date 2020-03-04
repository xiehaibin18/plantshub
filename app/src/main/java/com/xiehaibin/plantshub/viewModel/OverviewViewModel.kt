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

    val searchType: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(400)
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
        if (searchType.value == 400) {
            println(1111111111111)
            doAsync {
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
        } else {
            if (_search.isNullOrBlank()) {
                message.value = "请输入内容"
                return
            }
            if (searchType.value == 1){
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
            } else if (searchType.value == 0) {
                doAsync {
                    getListData.post(
                        "searchLocationPlants",
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
}
