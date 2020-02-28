package com.xiehaibin.plantshub.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.databinding.HomeFragmentBinding
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.view.activity.CameraActivity
import com.xiehaibin.plantshub.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import org.jetbrains.anko.support.v4.startActivity


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.home_fragment, container, false)
        val binding: HomeFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        binding.data = viewModel
        binding.lifecycleOwner = activity
        return binding.root
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

        home_user_button.setOnClickListener {
            val acctoken = CommonData.getInstance().getAccountToken()
            if (acctoken.isNullOrBlank() || acctoken == "tourists"){
                it.findNavController().navigate(R.id.action_homeFragment_to_userNotLoggedFragment)
            } else {
                it.findNavController().navigate(R.id.action_homeFragment_to_userLoggedFragment)
            }
        }
        // swiperRefreshLayout
        home_swiperRefreshLayout.setOnRefreshListener {
            if (home_swiperRefreshLayout.isRefreshing) {
                home_swiperRefreshLayout.isRefreshing = false
            }
        }


    }

}
