package com.xiehaibin.plantshub.model

import com.google.gson.Gson
import com.xiehaibin.plantshub.model.data.checkLoginData
import okhttp3.*
import java.io.IOException

class CheckLogin {
    fun post(
        account: String,
        password: String,
        url: String,
        callback: (resBoolean: Boolean, err_code: Int, msg: String) -> Unit
    ) {
        _post(account, password, url, callback)
    }

    private fun _post(
        account: String,
        password: String,
        url: String,
        callback: (resBoolean: Boolean, err_code: Int, msg: String) -> Unit
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
                callback(false, 1, e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val checkLoginData =
                    Gson().fromJson(response.body!!.string(), checkLoginData::class.java)
                val res: String
                res =
                    if (checkLoginData.err_code == 0) checkLoginData.account_token.toString() else checkLoginData.message.toString()
                callback(true, checkLoginData.err_code, res)
            }

        })
    }
}