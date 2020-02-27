package com.xiehaibin.plantshub.model

import com.google.gson.Gson
import com.xiehaibin.plantshub.model.data.PictureRecognitionData
import okhttp3.*
import java.io.IOException
import java.lang.Exception

class PictureRecognition {
    fun post(
        roles: String,
        imgStr: String,
        url: String,
        callback: (err_code: Int, msg: String) -> Unit
    ) {
        _post(roles, imgStr, url, callback)
    }

    private fun _post(
        _roles: String,
        _imgStr: String,
        _url: String,
        _callback: (err_code: Int, msg: String) -> Unit
    ) {
        val client = OkHttpClient()
        val formBody = FormBody.Builder()
            .add("roles", _roles)
            .add("picture", _imgStr)
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
                    val pictureRecognitionData = Gson().fromJson(response.body!!.string(), PictureRecognitionData::class.java)
                    // err_code 0识别成功，1失败失败，解析Json失败，500服务器错误，400本地出错
                    if (pictureRecognitionData.code == 0) {
                        _callback(pictureRecognitionData.code, pictureRecognitionData.data!!)
                    } else {
                        _callback(pictureRecognitionData.code, pictureRecognitionData.err!!)
                    }
                } catch (e: Exception) {
                    _callback(2, e.toString())
                }
            }

        })
    }
}