package com.xiehaibin.plantshub.model

import com.google.gson.Gson
import com.xiehaibin.plantshub.model.data.UserRegisterData
import okhttp3.*
import java.io.IOException

class UserRegister {
    fun post(
        account: String,
        password: String,
        nickname: String,
        phone: String,
        url: String,
        callback: (err_code: Int, msg: String) -> Unit
    ) {
        _post(account, password, nickname, phone, url, callback)
    }

    private fun _post(
        _account: String,
        _password: String,
        _nickname: String,
        _phone: String,
        _url: String,
        _callback: (err_code: Int, msg: String) -> Unit
    ) {
        val client = OkHttpClient()
        val formBody = FormBody.Builder()
            .add("account", _account)
            .add("password", _password)
            .add("nickname", _nickname)
            .add("phone", _phone)
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
                val userRegisterData = Gson().fromJson(response.body!!.string(), UserRegisterData::class.java)
                if (userRegisterData.err_code == 0) {
                    _callback(userRegisterData.err_code, userRegisterData.account_token!!)
                } else {
                    _callback(userRegisterData.err_code, userRegisterData.message!!)
                }
            }

        })
    }

}