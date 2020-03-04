package com.xiehaibin.plantshub.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.text.set
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
import org.jetbrains.anko.custom.customView
import org.jetbrains.anko.customView
import org.jetbrains.anko.editText
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.yesButton


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var homeFragmentData: Bundle

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
            if (CommonData.getInstance().getRouter() == 1001) {
                view!!.findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
            } else {
                view!!.findNavController().navigate(CommonData.getInstance().getRouter())
            }
        }

        home_identify_button.setOnClickListener {
            startActivity<CameraActivity>()
        }

        home_allLocation_button.setOnClickListener {
            CommonData.getInstance().setRouter(0)
            CommonData.getInstance().setOverviewDataType("allLocation")
            it.findNavController().navigate(R.id.action_homeFragment_to_overviewFragment)
        }

        home_allPlants_button.setOnClickListener {
            CommonData.getInstance().setRouter(0)
            CommonData.getInstance().setOverviewDataType("allPlants")
            it.findNavController().navigate(R.id.action_homeFragment_to_overviewFragment)
        }

        home_myLocation_button.setOnClickListener { view ->
            CommonData.getInstance().setRouter(0)
            CommonData.getInstance().setOverviewDataType("myLocationPlants")
            // 获取地理位置失败
            alert( "获取位置失败，手动输入您的位置吧", "我的位置") {
                var input: EditText? = null
                customView {
                    input = editText() {
                        hint = "请输入完整的省/市级行政区名称"
                    }
                    input!!.setText(CommonData.getInstance().getMyLocation())
                }
                yesButton {
                    CommonData.getInstance().setMyLocation(input?.text.toString())
                    val arguments = Bundle()
                    arguments.putString("search", input?.text.toString())
                    view.findNavController().navigate(R.id.action_homeFragment_to_overviewFragment, arguments)
                }
            }.show()
        }
        home_user_button.setOnClickListener {
            val acctoken = CommonData.getInstance().getAccountToken()
            if (acctoken.isNullOrBlank() || acctoken == "tourists") {
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
            CommonData.getInstance().setRouter(1001)
            // 提交数据给OverviewAdapter
            overviewAdapter.submitList(it)
            home_swiperRefreshLayout.isRefreshing = false
        })
        viewModel.message.observe(this, Observer {
            toast(it)
        })
        viewModel.overviewData.value?.size ?: viewModel.getData()
    }

    override fun onDestroy() {
        super.onDestroy()
        CommonData.getInstance().setRouter(0)
    }
    fun setHomeFragmentData(value: Bundle) {
        homeFragmentData = value
    }

}
