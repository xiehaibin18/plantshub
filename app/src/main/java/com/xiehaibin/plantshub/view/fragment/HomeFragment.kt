package com.xiehaibin.plantshub.view.fragment

//import com.android.volley.Request

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
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
import com.xiehaibin.plantshub.view.activity.CameraActivity
import com.xiehaibin.plantshub.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import okhttp3.*
import org.jetbrains.anko.support.v4.startActivity
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
        home_identify_button.setOnClickListener {
            startActivity<CameraActivity>()
        }

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

        // swiperRefreshLayout
        swiperRefreshLayout.setOnRefreshListener {

            if (swiperRefreshLayout.isRefreshing) {
                swiperRefreshLayout.isRefreshing = false
            }
        }


    }

}
