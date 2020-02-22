package com.xiehaibin.plantshub.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.model.CheckAccountToken
import com.xiehaibin.plantshub.model.data.CommonData

class IndexViewModel(application: Application) : AndroidViewModel(application) {
    var defValue = application.resources.getString(R.string.app_defValue)
    private var shpName: String = "user_info"
    private val shp: SharedPreferences =
        getApplication<Application>().getSharedPreferences(shpName, Context.MODE_PRIVATE)

    // 验证本地AccountToken是否为空
    private fun checkLocalAccountToken(): Boolean = shp.contains("AccountToken")

    // 获取本地AccountToken
    private fun getLocalAccountToken(): String = shp.getString("AccountToken", defValue)

    fun checkWebAccountToken(callback: (res: Int?, err: String?) -> Unit) {
        // 验证本地AccountToken,有则验证，无则跳转登录界面
        if (checkLocalAccountToken()) {
            // 获取本地验证本地AccountToken
            val accountToken: String = getLocalAccountToken()
            // 获取url
            val url: String = CommonData.getInstance().getChkAccountTokenUrl()
            var checkAccountToken = CheckAccountToken()
            // 获取验证结果
            checkAccountToken.getBoolean(accountToken, url, fun(res: Boolean, err: String) {
                if (res) {
                    callback(0, null)
                } else {
                    callback(1, err)
                }
            })
        } else {
            callback(2, null)
        }
    }
}
