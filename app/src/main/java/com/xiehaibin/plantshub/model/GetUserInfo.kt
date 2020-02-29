package com.xiehaibin.plantshub.model

import com.google.gson.Gson
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.model.data.UserInfoData
import okhttp3.*
import java.io.IOException

class GetUserInfo {
    fun post(
        type: String,
        accountToken: String,
        url: String,
        callback: (err_code: Int, msg: String?) -> Unit
    ) {
        _post(type, url, accountToken, callback)
    }

    private fun _post(
        _type: String,
        _accountToken: String,
        _url: String,
        _callback: (err_code: Int, msg: String?) -> Unit
    ) {
        if (_accountToken == "tourists") {
            return
        }
        val client = OkHttpClient()
        val formBody = FormBody.Builder()
            .add("type", _type)
            .add("accountToken", _accountToken)
            .build()
        val request = Request.Builder()
            .url(_url)
            .post(formBody)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                _callback(500, e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val userInfoData =
                        Gson().fromJson(response.body!!.string(), UserInfoData::class.java)
                    CommonData.getInstance().setUserName(userInfoData.data!![0].name!!)
                    CommonData.getInstance().setUserAvatar(userInfoData.data!![0].avatar!!)
                    _callback(0, "ok")
                } catch (e: Exception) {
                    _callback(500, "$e")
                }
            }

        })
    }
}