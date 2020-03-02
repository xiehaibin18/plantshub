package com.xiehaibin.plantshub.model

import com.google.gson.Gson
import com.xiehaibin.plantshub.model.data.PictureRecognitionData
import com.xiehaibin.plantshub.model.data.PictureRecognitionDataItem
import okhttp3.*
import java.io.IOException
import java.lang.Exception

class PictureRecognition {
    fun post(
        roles: String,
        imgStr: String,
        url: String,
        callback: (err_code: Int, msg: String?, data: String?) -> Unit
    ) {
        _post(roles, imgStr, url, callback)
    }

    private fun _post(
        _roles: String,
        _imgStr: String,
        _url: String,
        _callback: (err_code: Int, msg: String?, data: String?) -> Unit
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
                _callback(500, e.toString(),null)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val responseData = response.body!!.string()
                    val pictureRecognitionData = Gson().fromJson(responseData, PictureRecognitionData::class.java)
                    // err_code 0识别成功，1失败失败，解析Json失败，500服务器错误，400本地出错
                    if (pictureRecognitionData.code == 0) {
                        _callback(pictureRecognitionData.code,null, responseData)
                    } else {
                        _callback(pictureRecognitionData.code, pictureRecognitionData.err,null)
                    }
                } catch (e: Exception) {
                    _callback(2, e.toString(),null)
                }
            }

        })
    }
}