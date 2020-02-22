package com.xiehaibin.plantshub.view.fragment

//import com.android.volley.Request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.util.ThreadUtil
import com.xiehaibin.plantshub.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        home_textView.text = CommonData.getInstance().getAccountToken()
//        viewModel.accString.observe(this, Observer {
//            home_textView.text = it
//        })
//        viewModel.accString.value?:viewModel.fetchData()

        // volley demo
//        val queue = Volley.newRequestQueue(activity)
        val baseUrl: String = "http://10.0.2.2:3000"
        var url: String = "/api/adminGetLocationData"
//        val stringRequest = StringRequest(
//            Request.Method.POST,
//            baseUrl.plus(url),
//            Response.Listener<String> { response ->
//                home_textView.text = response
//            },
//            Response.ErrorListener { error ->
//                home_textView.text = error.toString()
//            }
//        )
//        queue.add(stringRequest)

        // imageLoader
//        val imageLoader: ImageLoader = ImageLoader(queue,object : ImageLoader.ImageCache {
////            private var cache: LruCache<String,Bitmap> = LruCache(50)
////            override fun getBitmap(url: String): Bitmap? {
////                return cache.get(url)
////            }
////            override fun putBitmap(url: String, bitmap: Bitmap) {
////                cache.put(url,bitmap)
////            }
////        })
////        var imageUrl: String = "http://10.0.2.2:3000/public/plants/03377978_u=2074496705,3560011917&fm=26&gp=0.jpg"
////        imageLoader.get(imageUrl,object : ImageLoader.ImageListener {
////            override fun onResponse(response: ImageLoader.ImageContainer, isImmediate: Boolean) {
////                home_imageView.setImageBitmap(response.bitmap)
////            }
////
////            override fun onErrorResponse(error: VolleyError?) {
////
////            }
////        })

        // OKHttp
        fun runOkhttp() {
            val client = OkHttpClient()
//        val MEDIA_TYPE_MARKDOWN = "application/json; charset=utf-8".toMediaType()
//        var json: JSONObject = JSONObject()
//        json.put("value",1)
            val formBody = FormBody.Builder()
                .add("type", "1")
                .add("adad", "adadad")
                .build()
            val request = Request
                .Builder()
                .url(baseUrl.plus(url))
                .post(formBody)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    home_textView.text = e.toString()
                }

                override fun onResponse(call: Call, response: okhttp3.Response) {
//                home_textView.text = response?.body?.string()
                    ThreadUtil.runOnMainThread(Runnable {
                        home_textView.text = response.body!!.string()
                    })
                }

            })
        }

        // swiperRefreshLayout
        swiperRefreshLayout.setOnRefreshListener {
            runOkhttp()
            if (swiperRefreshLayout.isRefreshing) {
                swiperRefreshLayout.isRefreshing = false
            }
        }


    }

}
