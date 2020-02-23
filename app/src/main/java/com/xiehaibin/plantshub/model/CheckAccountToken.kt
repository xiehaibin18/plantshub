package com.xiehaibin.plantshub.model

import okhttp3.*
import java.io.IOException

class CheckAccountToken {
    fun getBoolean(
        accountToken: String,
        url: String,
        callback: (res: Boolean, msg: String) -> Unit
    ) {
        post(accountToken, url, callback)
    }

    private fun post(
        accountToken: String,
        url: String,
        callback: (res: Boolean, msg: String) -> Unit
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
                callback(false, e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                callback(true, response.body!!.string())
            }

        })
    }
}