package com.xiehaibin.plantshub.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager

import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.adapter.OverviewAdapter
import com.xiehaibin.plantshub.model.data.AllPlantsDataItem
import com.xiehaibin.plantshub.viewModel.OverviewViewModel
import kotlinx.android.synthetic.main.overview_fragment.*
import org.jetbrains.anko.support.v4.toast

class OverviewFragment : DialogFragment() {

    companion object {
        fun newInstance() = OverviewFragment()
    }

    private val viewModel: OverviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.overview_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        overview_swiperRefreshLayout.setOnRefreshListener {
            viewModel.getOverviewData()
        }
        // 实例化OverviewAdapter
        val overviewAdapter = OverviewAdapter()
        // recyclerView设置
        overview_recyclerView.apply {
            adapter = overviewAdapter
            layoutManager = GridLayoutManager(requireContext(), 1)
        }
        viewModel.overviewData.observe(this, Observer {
            // 提交数据给OverviewAdapter
            overviewAdapter.submitList(it as MutableList<AllPlantsDataItem>?)
            overview_swiperRefreshLayout.isRefreshing = false
        })
        viewModel.message.observe(this, Observer {
            toast(it)
        })
        viewModel.getOverviewData()
    }



}
