package com.xiehaibin.plantshub.view.fragment.user

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
import com.xiehaibin.plantshub.adapter.UserMessageAdapter
import com.xiehaibin.plantshub.model.data.UserMessageDataItem
import com.xiehaibin.plantshub.view.fragment.DialogFragment
import com.xiehaibin.plantshub.viewModel.user.UserMessageViewModel
import kotlinx.android.synthetic.main.user_message_fragment.*
import org.jetbrains.anko.support.v4.toast

class UserMessageFragment : Fragment() {

    companion object {
        fun newInstance() =
            UserMessageFragment()
    }

    private val viewModel: UserMessageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_message_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 实例化UserMessageAdapter
        val userMessageAdapter = UserMessageAdapter()
        // recyclerView设置
        user_message_recyclerView.apply {
            adapter = userMessageAdapter
            layoutManager = GridLayoutManager(requireContext(), 1)
        }
        viewModel.userMessageAdapter.observe(this, Observer {
            // 提交数据给UserMessageAdapter
            userMessageAdapter.submitList(it)
            user_message_swipeRefreshLayout.isRefreshing = false
        })
        viewModel.message.observe(this, Observer {
            toast(it)
        })
        viewModel.getUserMessageAdapterData()
        // 下拉刷新
        user_message_swipeRefreshLayout.setOnRefreshListener {
            viewModel.getUserMessageAdapterData()
        }
        user_message_back_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_userMessageFragment_to_userLoggedFragment)
        }
    }

}
