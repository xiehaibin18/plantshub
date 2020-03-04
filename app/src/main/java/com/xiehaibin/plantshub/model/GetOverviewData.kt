package com.xiehaibin.plantshub.model

import com.google.gson.Gson
import com.xiehaibin.plantshub.model.data.AllPlantsData
import com.xiehaibin.plantshub.model.data.LocationDataItem
import okhttp3.*
import java.io.IOException

class GetOverviewData {
    fun post(
        type: String,
        url: String,
        callback: (err_code: Int, msg: String?, data: List<Any>?) -> Unit
    ) {
        _post(type, url, callback)
    }

    private fun _post(
        _type: String,
        _url: String,
        _callback: (err_code: Int, msg: String?, data: List<Any>?) -> Unit
    ) {
        val client = OkHttpClient()
        val formBody = FormBody.Builder()
            .add("type", _type)
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
                    val overviewData =
                        Gson().fromJson(response.body!!.string(), AllPlantsData::class.java)
                            .data.toList()
                    _callback(0, "ok", overviewData)
                } catch (e: Exception) {
                    _callback(500, "$e", null)
                }
            }

        })
    }
}