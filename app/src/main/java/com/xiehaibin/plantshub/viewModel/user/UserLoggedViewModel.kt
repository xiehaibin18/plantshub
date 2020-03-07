package com.xiehaibin.plantshub.viewModel.user

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.model.GetUserInfo
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.viewModel.IndexViewModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class UserLoggedViewModel(application: Application) : AndroidViewModel(application) {
    private var shpName: String = "user_info"
    private val shp: SharedPreferences =
        getApplication<Application>().getSharedPreferences(shpName, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = shp.edit()

    val userName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val userAvatar: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getUserInfo() {
        userName.value = CommonData.getInstance().getUserName()
        userAvatar.value = CommonData.getInstance().getUserAvatar()
//        if (userName.value.isNullOrBlank()) {
            val accountToken = CommonData.getInstance().getAccountToken()
            val url = CommonData.getInstance().getUserInfoUrl()
            val getUserInfo = GetUserInfo()
            doAsync {
                getUserInfo.post(
                    "getUserInfo",
                    accountToken,
                    url,
                    fun(err_code, msg) {
                        uiThread {
                            if (err_code == 0) {
                                userName.value = CommonData.getInstance().getUserName()
                                userAvatar.value = CommonData.getInstance().getUserAvatar()
                            } else {
                                getApplication<Application>().toast(msg ?: "错误")
                            }
                        }
                    })
//            }
        }
    }

    fun userLogout() {
        CommonData.getInstance().setAccountToken("")
        CommonData.getInstance().setUserName("")
        CommonData.getInstance().setUserAvatar("")
        editor.clear()
        editor.apply()
    }
}
