package com.xiehaibin.plantshub.view.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController

import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.view.activity.LoginActivity
import com.xiehaibin.plantshub.viewModel.user.UserNotLoggedViewModel
import kotlinx.android.synthetic.main.user_not_logged_fragment.*
import org.jetbrains.anko.support.v4.startActivity

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
        user_not_logged_login_button.setOnClickListener {
            startActivity<LoginActivity>()
        }
        user_not_logged_back_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_userNotLoggedFragment_to_homeFragment)
        }
    }

}
