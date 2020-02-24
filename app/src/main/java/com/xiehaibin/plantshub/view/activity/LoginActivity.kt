package com.xiehaibin.plantshub.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.databinding.ActivityLoginBinding
import com.xiehaibin.plantshub.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.*

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
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

        // 点击登录按钮
        login_submit_button.setOnClickListener {
            viewModel.clickLoginSubmitButton()
        }

        // 点击注册
        login_register_hint.setOnClickListener {
            startActivity<RegisterActivity>()
        }

        // 点击游客登陆
        login_tourists_hint.setOnClickListener {
            viewModel.setAccountToken("tourists")
            startActivity<MainActivity>()
        }
    }
}
