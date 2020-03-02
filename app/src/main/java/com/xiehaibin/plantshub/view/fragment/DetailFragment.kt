package com.xiehaibin.plantshub.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.viewModel.DetailViewModel
import kotlinx.android.synthetic.main.detail_fragment.*
import org.jetbrains.anko.support.v4.toast

class DetailFragment : DialogFragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var detailFragmentData: Bundle
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (CommonData.getInstance().getIsDialog()) {
            CommonData.getInstance().setIsDialog(false)
            // dialog方式打开
            viewModel.getDetailViewData(
                detailFragmentData.getInt("type", 400),
                detailFragmentData.getInt("itemUid", 400)
            )
        } else {
        }
        viewModel.message.observe(this, Observer {
            toast(it)
        })
    }

    fun setDetailFragmentData(value: Bundle) {
        detailFragmentData = value
    }

}
