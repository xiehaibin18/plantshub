package com.xiehaibin.plantshub.view.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.viewModel.user.UserNotLoggedViewModel

class UserNotLoggedFragment : Fragment() {

    companion object {
        fun newInstance() =
            UserNotLoggedFragment()
    }

    private val viewModel: UserNotLoggedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_not_logged_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}