package com.xiehaibin.plantshub.model

import com.google.gson.Gson
import com.xiehaibin.plantshub.model.data.UserFavoriteData
import okhttp3.*
import java.io.IOException

class GetUserFavoriteData {
    fun post(
        type: String,
        accountToken: String,
        url: String,
        callback: (err_code: Int, msg: String?, data: List<Any>?) -> Unit
    ) {
        _post(type, accountToken, url, callback)
    }

    private fun _post(
        _type: String,
        _accountToken: String,
        _url: String,
        _callback: (err_code: Int, msg: String?, data: List<Any>?) -> Unit
    ) {
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
                _callback(500, e.toString(), null)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val userFavoriteData =
                        Gson().fromJson(response.body!!.string(), UserFavoriteData::class.java).data!!.toList()
                    _callback(0, "ok", userFavoriteData)
                } catch (e: Exception) {
                    _callback(500, "$e", null)
                }
            }

        })
    }
}