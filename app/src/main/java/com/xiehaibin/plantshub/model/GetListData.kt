package com.xiehaibin.plantshub.model

import com.google.gson.Gson
import com.xiehaibin.plantshub.model.data.AllPlantsData
import com.xiehaibin.plantshub.model.data.AllPlantsDataItem
import okhttp3.*
import java.io.IOException

class GetListData {
    fun post(
        type: String,
        search: String?,
        url: String,
        callback: (err_code: Int, msg: String?, data: List<AllPlantsDataItem>?) -> Unit
    ) {
        _post(type, search, url, callback)
    }

    private fun _post(
        _type: String,
        _search: String?,
        _url: String,
        _callback: (err_code: Int, msg: String?, data: List<AllPlantsDataItem>?) -> Unit
    ) {
        val client = OkHttpClient()
        val formBody = FormBody.Builder()
            .add("type", _type)
            .add("search", _search?:"")
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
                        Gson().fromJson(response.body!!.string(), AllPlantsData::class.java)!!.data.toList()
                    _callback(0, "ok", userFavoriteData)
                } catch (e: Exception) {
                    _callback(500, "$e", null)
                }
            }

        })
    }
}