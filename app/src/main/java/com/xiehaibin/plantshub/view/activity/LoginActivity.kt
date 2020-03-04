package com.xiehaibin.plantshub.view.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.databinding.ActivityLoginBinding
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.view.fragment.DialogFragment
import com.xiehaibin.plantshub.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    companion object{
        var fa: Activity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CommonData.getInstance().setRouter(0)
        fa = this
        // DataBing
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.data = viewModel
        binding.lifecycleOwner = this

        // 观察accountErrorHint变化
        val accountErrorHintObserver = Observer<String> {
            login_account_textInputLayout.error = it
        }
        viewModel.accountErrorHint.observe(this, accountErrorHintObserver)

        // 观察passwordErrorHint变化
        val passwordErrorHintObserver = Observer<String> {
            login_password_textInputLayout.error = it
        }
        viewModel.passwordErrorHint.observe(this, passwordErrorHintObserver)

        // 观察submitButtonIsEnabled变化
        val submitButtonIsEnabledObserver = Observer<Boolean> {
            login_submit_button.isEnabled = it
        }
        viewModel.submitButtonIsEnabled.observe(this, submitButtonIsEnabledObserver)

        // 观察checkLoginStatus变化
        val checkLoginStatusObserver = Observer<Boolean> {
            if (it) {
                startActivity<MainActivity>()
                finish()
            } else {
                viewModel.submitButtonIsEnabled.value = true
                toast(viewModel.checkLoginMessage.value.toString())
            }
        }
        viewModel.checkLoginStatus.observe(this,checkLoginStatusObserver)

        // 点击注册
        login_register_hint.setOnClickListener {
            startActivity<RegisterActivity>()
        }

        // 点击游客登陆
        login_tourists_hint.setOnClickListener {
//            supportFragmentManager.let {
//                DialogFragment().show(it, "")
//            }
            viewModel.setAccountToken("tourists")
            startActivity<MainActivity>()
        }
    }
}
