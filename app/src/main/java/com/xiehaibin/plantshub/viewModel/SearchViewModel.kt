package com.xiehaibin.plantshub.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.xiehaibin.plantshub.model.GetOverviewData
import com.xiehaibin.plantshub.model.data.CommonData
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    val overviewData: MutableLiveData<List<Any>> by lazy {
        MutableLiveData<List<Any>>()
    }

    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getOverviewData() {
        // 获取url
        var url: String = CommonData.getInstance().getOverviewDataUrl()
        val getOverviewData = GetOverviewData()
        CommonData.getInstance().setOverviewDataType("study")
        doAsync {
            getOverviewData.post(
                CommonData.getInstance().getOverviewDataType(),
                url,
                fun(err_code, msg, data) {
                    uiThread {
                        if (err_code == 0 && data!!.isNotEmpty()) {
                            overviewData.value = data
                        } else {
                            if (data!!.isEmpty()) {
                                message.value = "无"
                            } else {
                                message.value = msg
                            }
                        }
                    }
                }
            )
        }
    }
}
