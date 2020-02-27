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
import androidx.navigation.findNavController
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

        home_search_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        // swiperRefreshLayout
        swiperRefreshLayout.setOnRefreshListener {

            if (swiperRefreshLayout.isRefreshing) {
                swiperRefreshLayout.isRefreshing = false
            }
        }


    }

}
