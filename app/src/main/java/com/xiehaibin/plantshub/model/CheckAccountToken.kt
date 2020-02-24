package com.xiehaibin.plantshub.model

import com.google.gson.Gson
import com.xiehaibin.plantshub.model.data.checkAccountToken
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
                val checkAccountToken = Gson().fromJson(response.body!!.string(),checkAccountToken::class.java)
                if (checkAccountToken.err_code == 0) {
                    callback(0, checkAccountToken.message)
                } else {
                    callback(checkAccountToken.err_code, checkAccountToken.message)
                }

            }

        })
    }
}