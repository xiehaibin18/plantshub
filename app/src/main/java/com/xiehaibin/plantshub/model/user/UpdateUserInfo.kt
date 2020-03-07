package com.xiehaibin.plantshub.model.user

import com.google.gson.Gson
import com.xiehaibin.plantshub.model.data.CommonData
import okhttp3.*
import java.io.IOException

class UpdateUserInfo {
    fun post(
        type: String,
        accountToken: String,
        oldData: String?,
        data: String,
        url: String,
        callback: (err_code: Int, msg: String?) -> Unit
    ) {
        _post(type, accountToken, oldData, data, url, callback)
    }

    private fun _post(
        _type: String,
        _accountToken: String,
        _oldData: String?,
        _data: String,
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
            .add("oldData", _oldData ?: "")
            .add("newData", _data)
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
                _callback(0, "ok")
            }

        })
    }
}