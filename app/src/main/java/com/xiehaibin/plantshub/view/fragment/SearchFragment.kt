package com.xiehaibin.plantshub.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager

import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.adapter.StudyAdapter
import com.xiehaibin.plantshub.model.data.AllPlantsDataItem
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.viewModel.SearchViewModel
import kotlinx.android.synthetic.main.search_fragment.*
import org.jetbrains.anko.support.v4.toast

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 实例化OverviewAdapter
        val studyAdapter = StudyAdapter()
        // recyclerView设置
        study_recyclerView.apply {
            adapter = studyAdapter
            layoutManager = GridLayoutManager(requireContext(), 1)
        }
        viewModel.overviewData.observe(this, Observer {
            // 提交数据给OverviewAdapter
            studyAdapter.submitList(it as MutableList<AllPlantsDataItem>?)
            study_swiperRefreshLayout.isRefreshing = false
        })
        viewModel.getOverviewData()
        study_swiperRefreshLayout.setOnRefreshListener {
            viewModel.getOverviewData()
            study_swiperRefreshLayout.isRefreshing = false
        }
        viewModel.message.observe(this, Observer {
            toast(it)
        })
        CommonData.getInstance().setRouter(0)
    }

}
