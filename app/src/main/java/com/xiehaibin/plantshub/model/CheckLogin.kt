package com.xiehaibin.plantshub.model

import okhttp3.*
import java.io.IOException

class CheckLogin {
    fun post(
        account: String,
        password: String,
        url: String,
        callback: (resBoolean: Boolean, msg: String) -> Unit
    ) {
        _post(account, password, url, callback)
    }

    private fun _post(
        account: String,
        password: String,
        url: String,
        callback: (resBoolean: Boolean, msg: String) -> Unit
    ) {
        val client = OkHttpClient()
        val formBody = FormBody.Builder()
            .add("account", account)
            .add("password", password)
            .build()
        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(false, e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                callback(true, response.body!!.string())
            }

        })
    }
}