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
import com.xiehaibin.plantshub.adapter.UserFavoriteAdapter
import com.xiehaibin.plantshub.viewModel.user.UserFavoriteViewModel
import kotlinx.android.synthetic.main.user_favorite_fragment.*
import org.jetbrains.anko.support.v4.toast

class UserFavoriteFragment : Fragment() {

    companion object {
        fun newInstance() =
            UserFavoriteFragment()
    }

    private val viewModel: UserFavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_favorite_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 实例化UserMessageAdapter
        val userFavoriteAdapter = UserFavoriteAdapter()
        // recyclerView设置
        user_favorite_recyclerView.apply {
            adapter = userFavoriteAdapter
            layoutManager = GridLayoutManager(requireContext(), 1)
        }
        viewModel.userFavoriteAdapter.observe(this, Observer {
            userFavoriteAdapter.submitList(it)
            user_favorite_swipeRefreshLayout.isRefreshing = false
        })
        viewModel.message.observe(this, Observer {
            toast(it)
        })
        viewModel.getUserFavoriteAdapterData()
        // 下拉刷新
        user_favorite_swipeRefreshLayout.setOnRefreshListener {
            viewModel.getUserFavoriteAdapterData()
        }
        user_favorite_back_imageButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_userFavoriteFragment_to_userLoggedFragment)
        }
    }

}
