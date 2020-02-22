package com.xiehaibin.plantshub.util

import android.os.Handler
import android.os.Looper

object ThreadUtil {
    val handler = Handler(Looper.getMainLooper())

    fun runOnMainThread(runnable: Runnable){
        handler.post(runnable)
    }
}