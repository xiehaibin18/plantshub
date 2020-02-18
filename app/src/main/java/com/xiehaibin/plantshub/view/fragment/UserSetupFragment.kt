package com.xiehaibin.plantshub.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.viewModel.UserSetupViewModel

class UserSetupFragment : Fragment() {

    companion object {
        fun newInstance() = UserSetupFragment()
    }

    private val viewModel: UserSetupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_setup_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
