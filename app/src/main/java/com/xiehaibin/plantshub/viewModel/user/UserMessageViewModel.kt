package com.xiehaibin.plantshub.viewModel.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.xiehaibin.plantshub.model.data.UserMessageDataItem

class UserMessageViewModel(application: Application) : AndroidViewModel(application) {
    val userMessageAdapter: MutableLiveData<List<UserMessageDataItem>> by lazy {
        MutableLiveData<List<UserMessageDataItem>>()
    }

    fun getUserMessageAdapterData() {

    }
}
