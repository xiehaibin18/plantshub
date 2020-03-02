package com.xiehaibin.plantshub.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.model.data.CommonData
import kotlinx.android.synthetic.main.dialog_fragment.*
import okhttp3.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread
import java.io.IOException

class DialogFragment : androidx.fragment.app.DialogFragment() {

    companion object {
        fun newInstance() = DialogFragment()
    }


    private lateinit var receiverInfo: Bundle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sender_dialog_name_textView.text = "回复：@${receiverInfo.getString("name")} ${receiverInfo.getString("content") }"
        sender_dialog_sendeButton.setOnClickListener {
            if (sender_dialog_content.text.isNullOrBlank()) {
                toast("请输入内容")
            } else {
                val accountToken = CommonData.getInstance().getAccountToken()
                doAsync {
                    val client = OkHttpClient()
                    val formBody = FormBody.Builder()
                        .add("accountToken", accountToken)
                        .add("type", "senderUserMessage")
                        .add("receiverUid", "${receiverInfo.getString("senderUid")}")
                        .add("content", "${sender_dialog_content.text}")
                        .add("messageType", "${receiverInfo.getInt("type")}")
                        .add("messageLocation", "${receiverInfo.getString("messageLocation")}")
                        .build()
                    val request = Request.Builder()
                        .url("http://192.168.0.105:3000/api/UserAddData")
                        .post(formBody)
                        .build()
                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            uiThread { toast("发送失败，$e") }
                        }

                        override fun onResponse(call: Call, response: Response) {
                            uiThread {
                                toast("发送成功")
                                it.dismiss()
                            }
                        }

                    })
                }
            }
        }
        sender_dialog_likeButton.setOnClickListener {
            doAsync {
                val client = OkHttpClient()
                val formBody = FormBody.Builder()
                    .add("type", "messageLike")
                    .add("messageId", "${receiverInfo.getString("messageId")}")
                    .build()
                val request = Request.Builder()
                    .url("http://192.168.0.105:3000/api/UserAddData")
                    .post(formBody)
                    .build()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        uiThread { toast("点赞失败，$e") }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        uiThread {
                            toast("点赞成功")
                            sender_dialog_likeButton.isEnabled = false
                        }
                    }

                })
            }
        }
    }

    fun receiverInfo(value: Bundle) {
        receiverInfo = value
    }

}
