package com.xiehaibin.plantshub.viewModel.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.xiehaibin.plantshub.model.GetUserMessageData
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.model.data.UserMessageData
import com.xiehaibin.plantshub.model.data.UserMessageDataItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class UserMessageViewModel(application: Application) : AndroidViewModel(application) {
    val userMessageAdapter: MutableLiveData<List<UserMessageDataItem>> by lazy {
        MutableLiveData<List<UserMessageDataItem>>()
    }
    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getUserMessageAdapterData() {
        // 获取url
        val url: String = CommonData.getInstance().getUserMessageUrl()
        // 获取accountToken
        val accountToken = CommonData.getInstance().getAccountToken()
        val getUserMessageData = GetUserMessageData()
        doAsync {
            getUserMessageData.post(
                "getUserMessage",
                accountToken,
                url,
                fun(err_code, msg, data) {
                    uiThread {
                        if (err_code == 0) {
                            userMessageAdapter.value = data as List<UserMessageDataItem>
                        } else {
                            message.value = msg
                        }
                    }
                }
            )
        }
    }
}
