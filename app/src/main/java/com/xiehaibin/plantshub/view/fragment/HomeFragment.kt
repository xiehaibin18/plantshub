package com.xiehaibin.plantshub.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.adapter.OverviewAdapter
import com.xiehaibin.plantshub.databinding.HomeFragmentBinding
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.view.activity.CameraActivity
import com.xiehaibin.plantshub.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


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
        if (CommonData.getInstance().getRouter() != 0) {
            view!!.findNavController().navigate(CommonData.getInstance().getRouter())
            when(CommonData.getInstance().getRouter()) {
                R.id.action_homeFragment_to_userLoggedFragment -> {
                    CommonData.getInstance().setRouter(0)
                }
            }
        }
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
            viewModel.getData()
        }
        // 实例化OverviewAdapter
        val overviewAdapter = OverviewAdapter()
        // recyclerView设置
        home_recyclerView.apply {
            adapter = overviewAdapter
            layoutManager = GridLayoutManager(requireContext(), 1)
        }
        viewModel.overviewData.observe(this, Observer {
            // 提交数据给OverviewAdapter
            overviewAdapter.submitList(it)
            home_swiperRefreshLayout.isRefreshing = false
        })
        viewModel.message.observe(this, Observer {
            toast(it)
        })
        viewModel.getData()
    }

}
