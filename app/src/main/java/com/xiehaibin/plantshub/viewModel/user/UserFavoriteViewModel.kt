package com.xiehaibin.plantshub.viewModel.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.xiehaibin.plantshub.model.GetUserFavoriteData
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.model.data.UserFavoriteDataItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class UserFavoriteViewModel(application: Application) : AndroidViewModel(application) {
    val userFavoriteAdapter: MutableLiveData<List<UserFavoriteDataItem>> by lazy {
        MutableLiveData<List<UserFavoriteDataItem>>()
    }
    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getUserFavoriteAdapterData () {
        // 获取url
        val url: String = CommonData.getInstance().getUserFavoriteUrl()
        // 获取accountToken
        val accountToken = CommonData.getInstance().getAccountToken()
        val getUserFavoriteData = GetUserFavoriteData()
        doAsync {
            getUserFavoriteData.post(
                "getUserFavorite",
                accountToken,
                url,
                fun(err_code, msg, data) {
                    uiThread {
                        if (err_code == 0) {
                            userFavoriteAdapter.value = data as List<UserFavoriteDataItem>
                        } else {
                            message.value = msg
                        }
                    }
                }
            )
        }
    }
}
