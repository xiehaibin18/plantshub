package com.xiehaibin.plantshub.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.xiehaibin.plantshub.model.data.*
import okhttp3.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.io.IOException

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val msg: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val name: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val isFavorite: MutableLiveData<Int> by lazy {
        MutableLiveData(0)
    }
    val like: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val picture: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val introduction: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val location: MutableLiveData<List<LocationItem>> by lazy {
        MutableLiveData<List<LocationItem>>()
    }
    val message: MutableLiveData<List<UserMessageDataItem>> by lazy {
        MutableLiveData<List<UserMessageDataItem>>()
    }
    val plants: MutableLiveData<List<AllPlantsDataItem>> by lazy {
        MutableLiveData<List<AllPlantsDataItem>>()
    }

    fun getDetailViewData(type: Int, itemUid: Int) {
        if (type != 400 && itemUid != 400) {
            doAsync {
                val client = OkHttpClient()
                val formBody = FormBody.Builder()
                    .add("type", "getDetailData")
                    .add("uidType", type.toString())
                    .add("itemUid", itemUid.toString())
                    .build()
                val request = Request.Builder()
                    .url("http://192.168.0.105:3000/api/UserGetData")
                    .post(formBody)
                    .build()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        uiThread { msg.value = "获取数据失败，$e" }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        uiThread {
                            val detailData = Gson().fromJson(response.body!!.string(), DetailData::class.java)
                            name.value = detailData.name
                            isFavorite.value = detailData.isFavorite
                            like.value = detailData.like
                            picture.value = detailData.picture
                            introduction.value = detailData.introduction
                            message.value = detailData.message!!.toList()
                            if (type == 0) {
                                location.value = detailData.location!!.toList()
                            } else {
                                plants.value = detailData.plants!!.toList()
                            }
                        }
                    }

                })
            }
        } else {
            msg.value = "获取数据失败"
        }
    }

    fun userFavorite (type: Int, itemUid: Int) {
        val accountToken = CommonData.getInstance().getAccountToken()
        val _isFavorite = isFavorite.value
        if (type != 400 && itemUid != 400) {
            doAsync {
                val client = OkHttpClient()
                val formBody = FormBody.Builder()
                    .add("type", "userDoFavorite")
                    .add("uidType", type.toString())
                    .add("itemUid", itemUid.toString())
                    .add("isFavorite", _isFavorite.toString())
                    .add("accountToken", accountToken)
                    .build()
                val request = Request.Builder()
                    .url("http://192.168.0.105:3000/api/UserAddData")
                    .post(formBody)
                    .build()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        uiThread {
                            if (_isFavorite == 0) {
                                msg.value = "收藏失败，$e"
                            } else {
                                msg.value = "取消收藏失败，$e"
                            }
                        }

                    }

                    override fun onResponse(call: Call, response: Response) {
                        uiThread {
                            if (_isFavorite == 0) {
                                msg.value = "收藏成功"
                                isFavorite.value = 1
                            } else {
                                msg.value = "取消收藏成功"
                                isFavorite.value = 0
                            }
                        }
                    }
                })
            }
        } else {
            msg.value = "获取数据失败"
        }
    }

}
