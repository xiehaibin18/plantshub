package com.xiehaibin.plantshub.model

import com.google.gson.Gson
import com.xiehaibin.plantshub.model.data.CheckAccountTokenData
import okhttp3.*
import java.io.IOException

class CheckAccountToken {
    fun post(
        accountToken: String,
        url: String,
        callback: (err_code: Int, msg: String) -> Unit
    ) {
        _post(accountToken, url, callback)
    }

    private fun _post(
        accountToken: String,
        url: String,
        callback: (err_code: Int, msg: String) -> Unit
    ) {
        val client = OkHttpClient()
        val formBody = FormBody.Builder()
            .add("accountToken", accountToken)
            .build()
        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(3, e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val checkAccountTokenData = Gson().fromJson(response.body!!.string(), CheckAccountTokenData::class.java)
                    callback(checkAccountTokenData.err_code, checkAccountTokenData.message)
                } catch (e: Exception) {
                    callback(500,"服务器出错")
                }
            }

        })
    }
}