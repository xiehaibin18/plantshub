package com.xiehaibin.plantshub.view.fragment.user

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bumptech.glide.Glide

import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.databinding.UserLoggedFragmentBinding
import com.xiehaibin.plantshub.viewModel.user.UserLoggedViewModel
import kotlinx.android.synthetic.main.overview_cell.view.*
import kotlinx.android.synthetic.main.user_logged_fragment.*

class UserLoggedFragment : Fragment() {

    companion object {
        fun newInstance() =
            UserLoggedFragment()
    }

    private val viewModel: UserLoggedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.user_logged_fragment, container, false)
        val binding: UserLoggedFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.user_logged_fragment, container, false)
        binding.data = viewModel
        binding.lifecycleOwner = activity
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.userName.value ?: viewModel.getUserInfo()
        viewModel.userName.observe(this, Observer {
            user_logged_name.text = "${it}欢迎您"
        })
        viewModel.userAvatar.observe(this, Observer {
            Glide.with(this)
                .load(it)
                .placeholder(R.drawable.ic_image_gary_24dp)
                .into(user_logged_userAvatar)
        })
        user_logged_back_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_userLoggedFragment_to_homeFragment)
        }
        user_logged_logout_button.setOnClickListener {
            viewModel.userLogout()
            it.findNavController().navigate(R.id.action_userLoggedFragment_to_homeFragment)
        }
    }

}
